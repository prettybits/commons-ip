package org.roda_project.commons_ip.model.impl.roda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.roda_project.commons_ip.model.AIP;
import org.roda_project.commons_ip.model.AIPInterface;
import org.roda_project.commons_ip.model.AIPReader;
import org.roda_project.commons_ip.model.IPContentType;
import org.roda_project.commons_ip.model.IPDescriptiveMetadata;
import org.roda_project.commons_ip.model.IPFile;
import org.roda_project.commons_ip.model.IPMetadata;
import org.roda_project.commons_ip.model.IPRepresentation;
import org.roda_project.commons_ip.model.MetadataType;
import org.roda_project.commons_ip.model.MetadataType.MetadataTypeEnum;
import org.roda_project.commons_ip.model.ParseException;
import org.roda_project.commons_ip.model.RepresentationContentType;
import org.roda_project.commons_ip.model.RepresentationStatus;
import org.roda_project.commons_ip.model.impl.BasicAIP;
import org.roda_project.commons_ip.utils.IPEnums.AIPState;
import org.roda_project.commons_ip.utils.IPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Roda AIP reader.
 *
 * @author Rui Castro (rui.castro@gmail.com)
 */
public class RodaAIPReader implements AIPReader {
  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(RodaAIPReader.class);

  /**
   * Constant string "id".
   */
  private static final String ID = "id";
  /**
   * Constant string "type".
   */
  private static final String TYPE = "type";
  /**
   * Constant string "representations".
   */
  private static final String REPRESENTATIONS = "representations";
  /**
   * Constant string "metadata".
   */
  private static final String METADATA = "metadata";
  /**
   * Constant string "descriptive".
   */
  private static final String DESCRIPTIVE = "descriptive";
  /**
   * Constant string "descriptiveMetadata".
   */
  private static final String DESCRIPTIVE_METADATA = "descriptiveMetadata";
  /**
   * Constant string "preservation".
   */
  private static final String PRESERVATION = "preservation";
  /**
   * Constant string "other".
   */
  private static final String OTHER = "other";
  /**
   * Constant string "schemas".
   */
  private static final String SCHEMAS = "schemas";
  /**
   * Constant string "documentation".
   */
  private static final String DOCUMENTATION = "documentation";

  /**
   * AIP path.
   */
  private final Path path;

  /**
   * Constructor.
   * 
   * @param path
   *          AIP path.
   */
  public RodaAIPReader(final Path path) {
    this.path = path;
  }

  /**
   * Read the AIP.
   * 
   * @return the {@link AIP}.
   * @throws ParseException
   *           if some error occurred.
   */
  @Override
  public AIPInterface read() throws ParseException {
    try {

      final AIPInterface aip = new RodaAIP(new BasicAIP());

      final ObjectMapper mapper = new ObjectMapper();
      final JsonNode json = mapper.readTree(this.path.resolve("aip.json").toFile());

      aip.setAncestors(Collections.singletonList(json.get("parentId").asText()));
      aip.setContentType(new IPContentType(json.get(TYPE).asText()));
      aip.setState(AIPState.valueOf(json.get("state").asText()));

      final Path mdPath = this.path.resolve(METADATA);

      readJsonDescriptiveMDs(aip, json);
      findAndAddPreservationMDs(aip::addPreservationMetadata);
      findAndAddOtherMDs(mdPath.resolve(OTHER), aip::addOtherMetadata);
      findIPFiles(this.path.resolve(SCHEMAS)).forEach(aip::addSchema);
      findIPFiles(this.path.resolve(DOCUMENTATION)).forEach(aip::addDocumentation);

      readRepresentations(aip, json);

      return aip;

    } catch (final IOException | IPException e) {
      LOGGER.debug("Error reading aip.json - " + e.getMessage(), e);
      throw new ParseException("Error reading aip.json", e);
    }
  }

  /**
   * Read {@link IPDescriptiveMetadata} records from <code>aip.json</code>
   * "descriptiveMetadata" field and update the {@link AIPInterface}.
   *
   * @param aip
   *          the {@link AIPInterface}.
   * @param json
   *          the JSON node.
   * @throws IPException
   *           if some error occurs.
   */
  private void readJsonDescriptiveMDs(final AIPInterface aip, final JsonNode json) throws IPException {
    if (json.has(DESCRIPTIVE_METADATA)) {
      final Iterator<JsonNode> iterator = json.get(DESCRIPTIVE_METADATA).elements();
      while (iterator.hasNext()) {
        aip.addDescriptiveMetadata(readJsonDescriptionMD(iterator.next()));
      }
    }
  }

  /**
   * Read {@link IPDescriptiveMetadata} from a {@link JsonNode}.
   *
   * @param json
   *          the JSON node.
   * @return the {@link IPDescriptiveMetadata}.
   */
  private IPDescriptiveMetadata readJsonDescriptionMD(final JsonNode json) {
    final String id = json.get(ID).asText();
    return new IPDescriptiveMetadata(id, new IPFile(this.path.resolve(METADATA).resolve(DESCRIPTIVE).resolve(id)),
      new MetadataType(json.get(TYPE).asText()), json.get("version").asText());
  }

  /**
   * Find preservation metadata files and adds them to {@link AIPInterface}.
   *
   * @param mdSetter
   *          the {@link IPMetadataSetter}.
   * @throws IOException
   *           if some I/O error occurs.
   * @throws IPException
   *           if some other error occurs.
   */
  private void findAndAddPreservationMDs(final IPMetadataSetter mdSetter) throws IOException, IPException {
    for (IPMetadata md : findPreservationMDs(this.path.resolve(METADATA).resolve(PRESERVATION))) {
      mdSetter.addMetadata(md);
    }
  }

  /**
   * Find other metadata files and for each one calls
   * {@link IPMetadataSetter#addMetadata(IPMetadata)}.
   *
   * @param omdPath
   *          the {@link Path} to other metadata files.
   * @param mdSetter
   *          the {@link IPMetadataSetter}.
   * @throws IOException
   *           if some I/O error occurs.
   * @throws IPException
   *           if some other error occurs.
   */
  private void findAndAddOtherMDs(final Path omdPath, final IPMetadataSetter mdSetter) throws IOException, IPException {
    if (Files.isDirectory(omdPath)) {
      final Iterator<Path> paths = Files.list(omdPath).iterator();
      while (paths.hasNext()) {
        final Path mdPath = paths.next();
        if (Files.isDirectory(mdPath)) {
          for (IPMetadata md : findMDs(mdPath, MetadataType.OTHER().setOtherType(mdPath.getFileName().toString()))) {
            mdSetter.addMetadata(md);
          }
        }
        if (Files.isRegularFile(mdPath)) {
          for (IPMetadata md : findMDs(mdPath, MetadataType.OTHER())) {
            mdSetter.addMetadata(md);
          }
        }
      }
    }
  }

  /**
   * Read {@link IPRepresentation} and update the {@link AIPInterface}.
   *
   * @param aip
   *          the {@link AIPInterface}.
   * @param json
   *          the JSON node.
   * @throws IPException
   *           if some error occurs.
   * @throws IOException
   *           if some I/O error occurs.
   */
  private void readRepresentations(final AIPInterface aip, final JsonNode json) throws IPException, IOException {
    if (json.has(REPRESENTATIONS)) {
      final Iterator<JsonNode> iterator = json.get(REPRESENTATIONS).elements();
      while (iterator.hasNext()) {
        aip.addRepresentation(readRepresentation(iterator.next()));
      }
    }
  }

  /**
   * Read {@link IPRepresentation}.
   *
   * @param json
   *          the JSON node.
   * @return the {@link IPRepresentation}.
   * @throws IOException
   *           if some I/O error occurs.
   * @throws IPException
   *           if some error occured.
   */
  private IPRepresentation readRepresentation(final JsonNode json) throws IOException, IPException {
    final IPRepresentation rep = new IPRepresentation(json.get(ID).asText());
    rep.setStatus(
      json.get("original").asBoolean() ? RepresentationStatus.getORIGINAL() : RepresentationStatus.getOTHER());
    rep.setContentType(new RepresentationContentType(json.get(TYPE).asText()));

    final Path repPath = this.path.resolve(REPRESENTATIONS).resolve(rep.getRepresentationID());
    final Path mdPath = repPath.resolve(METADATA);

    findIPFiles(repPath.resolve("data")).forEach(rep::addFile);
    findDescriptiveMDs(mdPath.resolve(DESCRIPTIVE)).forEach(rep::addDescriptiveMetadata);
    findPreservationMDs(mdPath.resolve(PRESERVATION)).forEach(rep::addPreservationMetadata);
    findAndAddOtherMDs(mdPath.resolve(OTHER), rep::addOtherMetadata);
    // TODO: ask for example of representation agents
    findIPFiles(repPath.resolve(SCHEMAS)).forEach(rep::addDocumentation);
    findIPFiles(repPath.resolve(DOCUMENTATION)).forEach(rep::addDocumentation);

    return rep;
  }

  /**
   * Find {@link IPDescriptiveMetadata}s in the given {@link Path}.
   *
   * @param dmdPath
   *          the {@link Path}.
   * @return the {@link List<IPDescriptiveMetadata>}.
   * @throws IOException
   *           if some I/O error occurs.
   */
  private List<IPDescriptiveMetadata> findDescriptiveMDs(final Path dmdPath) throws IOException {
    final List<IPDescriptiveMetadata> list = new ArrayList<>();
    if (Files.isDirectory(dmdPath)) {
      try (Stream<Path> paths = Files.walk(dmdPath)) {
        paths.forEach(filePath -> {
          if (Files.isRegularFile(filePath)) {
            list.add(new IPDescriptiveMetadata(FilenameUtils.removeExtension(filePath.getFileName().toString()),
              new IPFile(filePath), MetadataType.OTHER(), null));
          }
        });
      }
    }
    return list;
  }

  /**
   * Find {@link IPMetadata}s in the given {@link Path}.
   *
   * @param mdPath
   *          the {@link Path}.
   * @return the {@link List<IPMetadata>}.
   * @throws IOException
   *           if some I/O error occurs.
   */
  private List<IPMetadata> findPreservationMDs(final Path mdPath) throws IOException {
    return findMDs(mdPath, new MetadataType(MetadataTypeEnum.PREMIS));
  }

  /**
   * Find {@link IPMetadata}s in the given {@link Path}.
   *
   * @param mdPath
   *          the {@link Path}.
   * @param mdType
   *          the {@link MetadataType}.
   * @return the {@link List<IPMetadata>}.
   * @throws IOException
   *           if some I/O error occurs.
   */
  private List<IPMetadata> findMDs(final Path mdPath, final MetadataType mdType) throws IOException {
    final List<IPMetadata> list = new ArrayList<>();
    if (Files.isDirectory(mdPath)) {
      try (Stream<Path> paths = Files.walk(mdPath)) {
        paths.forEach(filePath -> {
          if (Files.isRegularFile(filePath)) {
            list.add(new IPMetadata(FilenameUtils.removeExtension(filePath.getFileName().toString()),
              new IPFile(filePath), mdType));
          }
        });
      }
    }
    return list;
  }

  /**
   * Find {@link IPFile}s in the given {@link Path}.
   *
   * @param filesPath
   *          the {@link Path}.
   * @return the {@link List<IPFile>}.
   * @throws IOException
   *           if some I/O error occurs.
   */
  private List<IPFile> findIPFiles(final Path filesPath) throws IOException {
    final List<IPFile> list = new ArrayList<>();
    if (Files.isDirectory(filesPath)) {
      try (Stream<Path> paths = Files.walk(filesPath)) {
        paths.forEach(filePath -> {
          if (Files.isRegularFile(filePath)) {
            list.add(new IPFile(filePath));
          }
        });
      }
    }
    return list;
  }

  /**
   * {@link IPMetadata} setter interface. Implementations of this interface
   * should add the {@link IPMetadata} records to themselves.
   */
  interface IPMetadataSetter {
    /**
     * Add the specified metadata.
     *
     * @param md
     *          the {@link IPMetadata}.
     * @throws IPException
     *           if some error occurred.
     */
    void addMetadata(IPMetadata md) throws IPException;
  }

}