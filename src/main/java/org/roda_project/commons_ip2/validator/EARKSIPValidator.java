package org.roda_project.commons_ip2.validator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.roda_project.commons_ip2.validator.aipComponents.aipFileSectionComponent.AipFileSectionComponent;
import org.roda_project.commons_ip2.validator.common.InstatiateMets;
import org.roda_project.commons_ip2.validator.component.MetsValidator;
import org.roda_project.commons_ip2.validator.component.administritiveMetadataComponent.AdministritiveMetadataComponentValidator;
import org.roda_project.commons_ip2.validator.component.descriptiveMetadataComponent.DescriptiveMetadataComponentValidator;
import org.roda_project.commons_ip2.validator.component.fileComponent.StructureComponentValidator;
import org.roda_project.commons_ip2.validator.component.fileSectionComponent.FileSectionComponentValidator;
import org.roda_project.commons_ip2.validator.component.metsRootComponent.MetsComponentValidator;
import org.roda_project.commons_ip2.validator.component.metsRootComponent.MetsHeaderComponentValidator;
import org.roda_project.commons_ip2.validator.component.structuralMapComponent.StructuralMapComponentValidator;
import org.roda_project.commons_ip2.validator.constants.Constants;
import org.roda_project.commons_ip2.validator.constants.ConstantsCSIPspec;
import org.roda_project.commons_ip2.validator.observer.ValidationObserver;
import org.roda_project.commons_ip2.validator.reporter.ReporterDetails;
import org.roda_project.commons_ip2.validator.reporter.ValidationReportOutputJson;
import org.roda_project.commons_ip2.validator.sipComponents.sipFileSectionComponent.SipFileSectionComponent;
import org.roda_project.commons_ip2.validator.sipComponents.sipMetsRootComponent.SipMetsComponent;
import org.roda_project.commons_ip2.validator.sipComponents.sipMetsRootComponent.SipMetsHdrComponent;
import org.roda_project.commons_ip2.validator.state.MetsValidatorState;
import org.roda_project.commons_ip2.validator.state.StructureValidatorState;
import org.roda_project.commons_ip2.validator.utils.ResultsUtils;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/** {@author João Gomes <jgomes@keep.pt>}. */
public class EARKSIPValidator {
  /** IP path. */
  private final Path earksipPath;

  /** {@link ValidationReportOutputJson}. */
  private final ValidationReportOutputJson validationReportOutputJson;
  /** {@link StructureComponentValidator}. */
  private final StructureComponentValidator structureComponent;
  /** the contextual structural state {@link StructureValidatorState}. */
  private final StructureValidatorState structureValidatorState;
  /** List of CSIP components to validate. */
  private final List<MetsValidator> csipComponents = new ArrayList<>();
  /** List of SIP components to validate. */
  private final List<MetsValidator> sipComponents = new ArrayList<>();
  /** List of AIP components to validate. */
  private final List<MetsValidator> aipComponents = new ArrayList<>();
  /** The contextual mets state {@link MetsValidatorState}. */
  private final MetsValidatorState metsValidatorState;

  /**
   * Initializes Validation Objects.
   *
   * @param reportOutputJson
   *          the {@link ValidationReportOutputJson}
   * @throws IOException
   *           if some I/O error occurs.
   * @throws ParserConfigurationException
   *           if some error occurred.
   * @throws SAXException
   *           if some error occurred.
   */
  public EARKSIPValidator(final ValidationReportOutputJson reportOutputJson)
    throws IOException, ParserConfigurationException, SAXException {

    this.earksipPath = reportOutputJson.getSipPath().toAbsolutePath().normalize();

    this.validationReportOutputJson = reportOutputJson;

    this.structureValidatorState = new StructureValidatorState(
      reportOutputJson.getSipPath().toAbsolutePath().normalize());
    this.structureComponent = new StructureComponentValidator();
    this.metsValidatorState = new MetsValidatorState();
    setupComponents();
  }

  /**
   * Setup Validation Components.
   *
   * @throws IOException
   *           if some I/O error occurs.
   * @throws ParserConfigurationException
   *           if some error occur.
   * @throws SAXException
   *           if some error occur.
   */
  private void setupComponents() throws IOException, ParserConfigurationException, SAXException {
    this.csipComponents.add(new MetsComponentValidator());
    this.csipComponents.add(new MetsHeaderComponentValidator());
    this.csipComponents.add(new DescriptiveMetadataComponentValidator());
    this.csipComponents.add(new AdministritiveMetadataComponentValidator());
    this.csipComponents.add(new FileSectionComponentValidator());
    this.csipComponents.add(new StructuralMapComponentValidator());

    this.sipComponents.add(new SipMetsComponent());
    this.sipComponents.add(new SipMetsHdrComponent());
    this.sipComponents.add(new SipFileSectionComponent());

    this.aipComponents.add(new AipFileSectionComponent());
  }

  /**
   * Add {@link ValidationObserver} to the lists of observers.
   *
   * @param observer
   *          the {@link ValidationObserver}
   */
  public void addObserver(final ValidationObserver observer) {
    structureComponent.addObserver(observer);
    csipComponents.forEach(c -> c.addObserver(observer));
    sipComponents.forEach(c -> c.addObserver(observer));
  }

  /**
   * Remove {@link ValidationObserver} from the lists of observers.
   *
   * @param observer
   *          the {@link ValidationObserver}
   */
  public void removeObserver(final ValidationObserver observer) {
    structureComponent.removeObserver(observer);
    csipComponents.forEach(c -> c.removeObserver(observer));
    sipComponents.forEach(c -> c.removeObserver(observer));
  }

  /**
   * Validates the Information Package.
   *
   * @return if the Information package is valid or not
   * @throws IOException
   *           if some I/O error occurs.
   */
  public boolean validate() throws IOException {
    structureComponent.notifyObserversIPValidationStarted();
    final Map<String, ReporterDetails> structureValidationResults = structureComponent
      .validate(structureValidatorState);
    validationReportOutputJson.getResults().putAll(structureValidationResults);

    if (validationReportOutputJson.validFileComponent()) {
      final Map<String, InputStream> subMets;
      if (structureValidatorState.isZipFileFlag()) {
        metsValidatorState.setMetsFiles(structureValidatorState.getZipManager().getFiles(earksipPath));
        subMets = structureValidatorState.getZipManager().getSubMets(earksipPath);
      } else {
        metsValidatorState.setMetsFiles(structureValidatorState.getFolderManager().getFiles(earksipPath));
        subMets = structureValidatorState.getFolderManager().getSubMets(earksipPath);
      }

      if (subMets.size() > 0) {
        validateSubMets(subMets, structureValidatorState.isZipFileFlag());
      }
      validateRootMets();

      if (!validationReportOutputJson.getResults()
        .containsKey(ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP0_ID)) {
        final ReporterDetails csipStr0 = new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, "", true,
          false);
        csipStr0.setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION);
        validationReportOutputJson.getResults().put(ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP0_ID,
          csipStr0);
      }
    }
    writeReport();
    return validationReportOutputJson.getErrors() == 0;
  }

  /**
   * Iterates over all components and merge all results from components
   * validations.
   *
   * @throws IOException
   *           if some I/O error occurs.
   */
  private void validateComponents() throws IOException {
    for (MetsValidator component : csipComponents) {
      final Map<String, ReporterDetails> componentResults = component.validate(structureValidatorState,
        metsValidatorState);
      ResultsUtils.mergeResults(validationReportOutputJson.getResults(), componentResults);
    }
    validateIpTypeExtendedComponents();
  }

  /**
   * Validate METS files inside representations.
   *
   * @param subMets
   *          the {@link Map } with path to sub METS and InputStream of file.
   * @param isZip
   *          flag if the Information Package is in compact format or if it is a
   *          folder.
   */
  private void validateSubMets(final Map<String, InputStream> subMets, final boolean isZip) {
    for (Map.Entry<String, InputStream> entry : subMets.entrySet()) {

      final InstatiateMets instatiateMets = new InstatiateMets(entry.getValue());
      try {
        metsValidatorState.setMets(instatiateMets.instatiateMetsFile());
        metsValidatorState.setIpType(metsValidatorState.getMets().getMetsHdr().getOAISPACKAGETYPE());
        setupMetsValidatorState(entry.getKey(), isZip, false);
        validateComponents();
      } catch (IOException | JAXBException | SAXException e) {
        final String message = createExceptionMessage(e, entry.getKey());
        final ReporterDetails csipStr0 = new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, message,
          false, false);
        csipStr0.setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION);
        ResultsUtils.addResult(validationReportOutputJson.getResults(),
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP0_ID, csipStr0);
      }
    }
  }

  /**
   * Creates Message for Exception.
   *
   * @param e
   *          the {@link Exception}
   * @param mets
   *          the path to METS file
   * @return a message
   */
  private String createExceptionMessage(final Exception e, final String mets) {
    final StringBuilder message = new StringBuilder();

    Throwable cause = e;
    if (e.getMessage() != null) {
      message.append(Constants.OPEN_SQUARE_BRACKET).append(e.getClass().getSimpleName())
        .append(Constants.CLOSE_SQUARE_BRACKET).append(Constants.EMPTY_SPACE).append(e.getMessage());
    }
    while (cause.getCause() != null) {
      cause = cause.getCause();
      if (message.length() > 0) {
        message.append(" caused by ");
      }

      message.append(Constants.OPEN_SQUARE_BRACKET).append(cause.getClass().getSimpleName())
        .append(Constants.CLOSE_SQUARE_BRACKET).append(Constants.EMPTY_SPACE).append(cause.getMessage());

      if (cause instanceof SAXParseException) {
        final SAXParseException e1 = (SAXParseException) cause;
        message.append(" (file: ").append(mets).append(", line: ").append(e1.getLineNumber()).append(", column: ")
          .append(e1.getColumnNumber()).append(")");
      }
    }

    return message.toString();
  }

  /** Validates METS file in root of Information Package. */
  private void validateRootMets() {
    final InputStream metsRootStream;
    final String ipPath;
    try {

      if (structureValidatorState.isZipFileFlag()) {
        metsRootStream = structureValidatorState.getZipManager().getMetsRootInputStream(earksipPath);
        ipPath = earksipPath.toString();
      } else {
        metsRootStream = structureValidatorState.getFolderManager().getMetsRootInputStream(earksipPath);
        ipPath = earksipPath.resolve(Constants.METS_FILE).toString();
      }

      final InstatiateMets metsRoot = new InstatiateMets(metsRootStream);
      metsValidatorState.setMetsPath(earksipPath.toString());
      metsValidatorState.setMetsName(ipPath);
      metsValidatorState.setIsRootMets(true);

      metsValidatorState.setMets(metsRoot.instatiateMetsFile());
      validateComponents();
    } catch (IOException | JAXBException | SAXException e) {
      final String message = createExceptionMessage(e,
        earksipPath.toString() + Constants.SEPARATOR + Constants.METS_FILE);
      final ReporterDetails csipStr0 = new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, message,
        false, false);
      csipStr0.setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION);
      ResultsUtils.addResult(validationReportOutputJson.getResults(),
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP0_ID, csipStr0);
    }
  }

  /**
   * Setup State of METS.
   *
   * @param key
   *          the METS file path
   * @param isZip
   *          Flag if Information package is compacted or not
   * @param isRootMets
   *          Flag if METS file is root or representation METS
   */
  private void setupMetsValidatorState(final String key, final boolean isZip, final boolean isRootMets) {
    this.metsValidatorState.setMetsName(key);
    this.metsValidatorState.setIsRootMets(isRootMets);
    if (isZip) {
      final StringBuilder metsPath = new StringBuilder();
      for (String path : key.split(Constants.SEPARATOR)) {
        if (!path.equals(Constants.METS_FILE)) {
          metsPath.append(path).append("/");
        }
      }
      this.metsValidatorState.setMetsPath(metsPath.toString());
    } else {
      this.metsValidatorState.setMetsPath(Paths.get(key).getParent().toString());
    }
  }

  /** Notify all observers. */
  public void notifyIndicatorsObservers() {
    structureComponent.notifyIndicators(this.validationReportOutputJson.getErrors(),
      this.validationReportOutputJson.getSuccess(), this.validationReportOutputJson.getWarnings(),
      this.validationReportOutputJson.getNotes(), this.validationReportOutputJson.getSkipped());
  }

  /**
   * Validate SIP specifications or AIP Specifications if the type is SIP or AIP.
   *
   * @throws IOException
   *           if some I/O error occurs.
   */
  private void validateIpTypeExtendedComponents() throws IOException {
    if (metsValidatorState.getIpType() != null && metsValidatorState.getIpType().equals("SIP")) {
      validateSIPComponents();
    } else if (metsValidatorState.getIpType() != null && metsValidatorState.getIpType().equals("AIP")) {
      validateAIPComponets();
    }
  }

  /**
   * Iterate over SIP components and merge the results with CSIP validations.
   *
   * @throws IOException
   *           if some I/O error occurs.
   */
  private void validateSIPComponents() throws IOException {
    aipComponents.clear();
    for (MetsValidator component : sipComponents) {
      if (component instanceof SipFileSectionComponent) {
        ((SipFileSectionComponent) component).setIsToValidate(ResultsUtils.isResultValid(
          validationReportOutputJson.getResults(), ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP58_ID));
      }
      if (component instanceof SipMetsHdrComponent) {
        ((SipMetsHdrComponent) component).setIsToValidateMetsHdr(ResultsUtils.isResultValid(
          validationReportOutputJson.getResults(), ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP117_ID));
        if (validationReportOutputJson.getResults()
          .get(ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP10_ID) != null) {
          ((SipMetsHdrComponent) component).setIsToValidateAgents(ResultsUtils.isResultValid(
            validationReportOutputJson.getResults(), ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP10_ID));
        }
      }
      final Map<String, ReporterDetails> sipComponentResults = component.validate(structureValidatorState,
        metsValidatorState);
      ResultsUtils.mergeResults(validationReportOutputJson.getResults(), sipComponentResults);
    }
  }

  /**
   * Iterate over AIP components and merges the results with CSIP validations
   * results.
   *
   * @throws IOException
   *           if some I/O error occurs.
   */
  private void validateAIPComponets() throws IOException {
    sipComponents.clear();
    for (MetsValidator component : aipComponents) {
      if (component instanceof AipFileSectionComponent) {
        ((AipFileSectionComponent) component).setIsToValidate(ResultsUtils.isResultValid(
          validationReportOutputJson.getResults(), ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP58_ID));
      }
      final Map<String, ReporterDetails> aipComponentResults = component.validate(structureValidatorState,
        metsValidatorState);
      ResultsUtils.mergeResults(validationReportOutputJson.getResults(), aipComponentResults);
    }
  }

  /**
   * Write the report.
   *
   * @throws IOException
   *           if some I/O error occurs.
   */
  private void writeReport() throws IOException {
    if (metsValidatorState.getMets() != null) {
      validationReportOutputJson.setIpType(metsValidatorState.getIpType());
    }

    validationReportOutputJson.init();
    validationReportOutputJson.validationResults();
    validationReportOutputJson.writeFinalResult();
    notifyIndicatorsObservers();
    validationReportOutputJson.close();
    structureComponent.notifyObserversIPValidationFinished();
  }
}
