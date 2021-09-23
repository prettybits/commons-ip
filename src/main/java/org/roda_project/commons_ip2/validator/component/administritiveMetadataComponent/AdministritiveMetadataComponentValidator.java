package org.roda_project.commons_ip2.validator.component.administritiveMetadataComponent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.roda_project.commons_ip2.mets_v1_12.beans.AmdSecType;
import org.roda_project.commons_ip2.mets_v1_12.beans.MdSecType;
import org.roda_project.commons_ip2.validator.common.ControlledVocabularyParser;
import org.roda_project.commons_ip2.validator.common.MetsParser;
import org.roda_project.commons_ip2.validator.component.ValidatorComponentImpl;
import org.roda_project.commons_ip2.validator.constants.Constants;
import org.roda_project.commons_ip2.validator.constants.ConstantsCSIPspec;
import org.roda_project.commons_ip2.validator.handlers.MetsHandler;
import org.roda_project.commons_ip2.validator.reporter.ReporterDetails;
import org.roda_project.commons_ip2.validator.utils.CHECKSUMTYPE;
import org.roda_project.commons_ip2.validator.utils.Message;
import org.roda_project.commons_ip2.validator.utils.MetadataType;
import org.roda_project.commons_ip2.validator.utils.ResultsUtils;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * @author João Gomes <jgomes@keep.pt>
 */
public class AdministritiveMetadataComponentValidator extends ValidatorComponentImpl {
  public static final String METS_OBJECTID_CAN_T_BE_NULL = "mets/@OBJECTID can't be null";
  public static final String UTF_8 = "UTF-8";
  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AdministritiveMetadataComponentValidator.class);

  private final String moduleName;
  private List<AmdSecType> amdSec;
  private List<String> dmdSecStatus;


  public AdministritiveMetadataComponentValidator() throws IOException, ParserConfigurationException, SAXException {
    this.moduleName = Constants.CSIP_MODULE_NAME_4;
    this.dmdSecStatus = ControlledVocabularyParser.parse(Constants.PATH_RESOURCES_CSIP_VOCABULARY_DMD_SEC_STATUS);
  }

  @Override
  public Map<String,ReporterDetails> validate() throws IOException {
    Map<String, ReporterDetails> results = new HashMap<>();
    amdSec = mets.getAmdSec();
    ReporterDetails csip;
    /* CSIP31 */
    notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP31_ID);
    ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP31_ID,
      validateCSIP31().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

    /* CSIP32 */
    notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP32_ID);
    ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP32_ID,
      validateCSIP32().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

    if (ResultsUtils.isResultValid(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP32_ID)) {

      /* CSIP33 */
      notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP33_ID);
      ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP33_ID,
        validateCSIP33().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

      /* CSIP34 */
      notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP34_ID);
      ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP34_ID,
        validateCSIP34().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

      /* CSIP35 */
      notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP35_ID);
      ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP35_ID,
        validateCSIP35().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

      if (ResultsUtils.isResultValid(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP35_ID)) {

        /* CSIP36 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP36_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP36_ID,
          validateCSIP36().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP37 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP37_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP37_ID,
          validateCSIP37().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP38 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP38_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP38_ID,
          validateCSIP38().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP39 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP39_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP39_ID,
          validateCSIP39().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP40 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP40_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP40_ID,
          validateCSIP40().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP41 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP41_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP41_ID,
          validateCSIP41().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP42 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP42_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP42_ID,
          validateCSIP42().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP43 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP43_ID);
        try {
          csip = validateCSIP43().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION);
        } catch (Exception e) {
          csip = new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
            Message.createErrorMessage("Can't calculate checksum of file %1$s", metsName, isRootMets()), false, false);
        }
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP43_ID, csip);

        /* CSIP44 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP44_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP44_ID,
          validateCSIP44().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

      } else {
        String message = Message.createErrorMessage(
          "SKIPPED in %1$s because mets/amdSec/digiprovMD/mdRef doesn't exist", metsName, isRootMets());

        ResultsUtils.addResults(results,new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, message, true, true),
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP36_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP37_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP38_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP39_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP40_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP41_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP42_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP43_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP44_ID);
      }

    } else {
      String message = Message.createErrorMessage("SKIPPED in %1$s because mets/amdSec/digiprovMD doesn't exist",
        metsName, isRootMets());

      ResultsUtils.addResults(results,new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, message, true, true),
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP33_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP34_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP35_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP36_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP37_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP38_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP39_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP40_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP41_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP42_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP43_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP44_ID);
    }

    /* CSIP45 */
    notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP45_ID);
    ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP45_ID,
      validateCSIP45().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

    if (ResultsUtils.isResultValid(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP45_ID)) {
      /* CSIP46 */
      notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP46_ID);
      ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP46_ID,
        validateCSIP46().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

      /* CSIP47 */
      notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP47_ID);
      ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP47_ID,
        validateCSIP47().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

      /* CSIP48 */
      notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP48_ID);
      ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP48_ID,
        validateCSIP48().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

      if (ResultsUtils.isResultValid(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP48_ID)) {

        /* CSIP49 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP49_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP49_ID,
          validateCSIP49().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP50 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP50_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP50_ID,
          validateCSIP50().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP51 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP51_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP51_ID,
          validateCSIP51().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP52 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP52_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP52_ID,
          validateCSIP52().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP53 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP53_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP53_ID,
          validateCSIP53().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP54 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP54_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP54_ID,
          validateCSIP54().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP55 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP55_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP55_ID,
          validateCSIP55().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

        /* CSIP56 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP56_ID);
        try {
          csip = validateCSIP56().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION);
        } catch (Exception e) {
          csip = new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
            Message.createErrorMessage("Can't calculate checksum of file %1$s", metsName, isRootMets()), false, false);
        }
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP56_ID, csip);

        /* CSIP57 */
        notifyObserversValidationStarted(moduleName, ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP57_ID);
        ResultsUtils.addResult(results,ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP57_ID,
          validateCSIP57().setSpecification(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION));

      } else {
        String message = Message.createErrorMessage("SKIPPED in %1$s because mets/amdSec/rightsMD/mdRef doesn't exist",
          metsName, isRootMets());

        ResultsUtils.addResults(results,new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, message, true, true),
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP49_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP50_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP51_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP52_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP53_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP54_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP55_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP56_ID,
          ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP57_ID);

      }
    } else {
      ReporterDetails skippedCSIP =  new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
              Message.createErrorMessage("SKIPPED in %1$s because mets/amdSec/rightsMD doesn't exist", metsName,
                      isRootMets()),
              true, true);
      ResultsUtils.addResults(results,
       skippedCSIP,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP46_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP47_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP48_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP49_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP50_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP51_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP52_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP53_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP54_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP55_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP56_ID,
        ConstantsCSIPspec.VALIDATION_REPORT_SPECIFICATION_CSIP57_ID);
    }
    notifyObserversFinishModule(moduleName);
    return results;
  }

  /*
   * mets/amdSec If administrative / preservation metadata is available, it must
   * be described using the administrative metadata section ( <amdSec> ) element.
   * All administrative metadata is present in a single <amdSec> element. It is
   * possible to transfer metadata in a package using just the descriptive
   * metadata section and/or administrative metadata section.
   */
  private ReporterDetails validateCSIP31() throws IOException {
    if (isZipFileFlag()) {
      String regex;
      if (isRootMets()) {
        String OBJECTID = mets.getOBJID();
        if (OBJECTID != null) {
          regex = OBJECTID + "/metadata/.*";
        } else {
          return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, METS_OBJECTID_CAN_T_BE_NULL,
            false, false);
        }
      } else {
        regex = metsPath + "metadata/.*";
      }
      if (amdSec == null || amdSec.isEmpty()) {
        if (zipManager.countMetadataFiles(getEARKSIPpath(), regex) != 0
          && (mets.getDmdSec() == null || mets.getDmdSec().isEmpty())) {
          return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
            Message.createErrorMessage(
              "You have files in the metadata/folder, you must have mets/dmdSec or mets/amdSec in %1$s", metsName,
              isRootMets()),
            false, false);
        }
      } else {
        if (zipManager.countMetadataFiles(getEARKSIPpath(), regex) == 0) {
          for (AmdSecType amd : amdSec) {
            if (amd.getDigiprovMD() != null && !amd.getDigiprovMD().isEmpty()) {
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                Message.createErrorMessage(
                  "Doesn't have files in metadata folder but have amdSec in %1$s. Put the files under metadata folder",
                  metsName, isRootMets()),
                false, false);
            }
          }
        } else {
          HashMap<String, Boolean> metadataFiles = zipManager.getMetadataFiles(getEARKSIPpath(), regex);
          for (AmdSecType amd : mets.getAmdSec()) {
            for (MdSecType md : amd.getDigiprovMD()) {
              MdSecType.MdRef mdRef = md.getMdRef();
              if (mdRef != null) {
                String hrefDecoded = URLDecoder.decode(mdRef.getHref(), UTF_8);
                if (isRootMets()) {
                  if (metadataFiles.containsKey(mets.getOBJID() + "/" + hrefDecoded)) {
                    metadataFiles.replace(mets.getOBJID() + "/" + hrefDecoded, true);
                  }
                } else {
                  if (metadataFiles.containsKey(metsPath + hrefDecoded)) {
                    metadataFiles.replace(metsPath + hrefDecoded, true);
                  }
                }
              }
            }
          }
          if (metadataFiles.containsValue(false)) {
            for (MdSecType md : mets.getDmdSec()) {
              MdSecType.MdRef mdRef = md.getMdRef();
              if (mdRef != null) {
                String hrefDecoded = URLDecoder.decode(mdRef.getHref(), UTF_8);
                if (isRootMets()) {
                  if (metadataFiles.containsKey(mets.getOBJID() + "/" + hrefDecoded)) {
                    metadataFiles.replace(mets.getOBJID() + "/" + hrefDecoded, true);
                  }
                } else {
                  if (metadataFiles.containsKey(metsPath + hrefDecoded)) {
                    metadataFiles.replace(metsPath + hrefDecoded, true);
                  }
                }
              }
            }
            if (metadataFiles.containsValue(false)) {
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                Message.createErrorMessage("Have metadata files not referenced in %1$s", metsName, isRootMets()), false,
                false);
            }
          }
        }
      }
    } else {
      if (amdSec == null || amdSec.isEmpty()) {
        if (folderManager.countMetadataFiles(Paths.get(metsPath)) != 0
          && (mets.getDmdSec() == null || mets.getDmdSec().isEmpty())) {
          return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
            Message.createErrorMessage(
              "You have files in the metadata/folder, you must have mets/dmdSec or mets/amdSec in %1$s", metsName,
              isRootMets()),
            false, false);
        }
      } else {
        if (folderManager.countMetadataFiles(Paths.get(metsPath)) == 0) {
          for (AmdSecType amd : amdSec) {
            if (amd.getDigiprovMD() != null && !amd.getDigiprovMD().isEmpty()) {
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                Message.createErrorMessage(
                  "Doesn't have files in metadata folder but have amdSec in %1$s. Put the files under metadata folder",
                  metsName, isRootMets()),
                false, false);
            }
          }
        } else {
          HashMap<String, Boolean> metadataFiles = folderManager.getMetadataFiles(Paths.get(metsPath));
          for (AmdSecType amd : mets.getAmdSec()) {
            for (MdSecType md : amd.getDigiprovMD()) {
              MdSecType.MdRef mdRef = md.getMdRef();
              if (mdRef != null) {
                String hrefDecoded = URLDecoder.decode(mdRef.getHref(), UTF_8);
                if (hrefDecoded != null) {
                  String path = Paths.get(metsPath).resolve(hrefDecoded).toString();
                  if (metadataFiles.containsKey(path)) {
                    metadataFiles.replace(path, true);
                  }
                }
              }
            }
          }
          if (metadataFiles.containsValue(false)) {
            for (MdSecType md : mets.getDmdSec()) {
              MdSecType.MdRef mdRef = md.getMdRef();
              if (mdRef != null) {
                String hrefDecoded = URLDecoder.decode(mdRef.getHref(), UTF_8);
                if (hrefDecoded != null) {
                  String path = Paths.get(metsPath).resolve(hrefDecoded).toString();
                  if (metadataFiles.containsKey(path)) {
                    metadataFiles.replace(path, true);
                  }
                }
              }
            }
            if (metadataFiles.containsValue(false)) {
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                Message.createErrorMessage("Have metadata files not referenced in %1$s", metsName, isRootMets()), false,
                false);
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/digiprovMD For recording information about preservation the
   * standard PREMIS is used. It is mandatory to include one <digiprovMD> element
   * for each piece of PREMIS metadata. The use if PREMIS in METS is following the
   * recommendations in the 2017 version of PREMIS in METS Guidelines.
   */
  private ReporterDetails validateCSIP32() throws IOException {
    if (amdSec != null && !amdSec.isEmpty()) {
      int countDigiProvMd = 0;
      int countMdRefs = 0;
      for (AmdSecType a : amdSec) {
        List<MdSecType> digiprov = a.getDigiprovMD();
        if (!digiprov.isEmpty()) {
          countDigiProvMd++;
          for (MdSecType md : digiprov) {
            if (md.getMdRef() != null) {
              countMdRefs++;
            }
          }
        }
      }
      if (countDigiProvMd != countMdRefs) {
        return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
          Message.createErrorMessage(
            "It is mandatory to include one <digiprovMD> element in %1$s for each piece of PREMIS metadata", metsName,
            isRootMets()),
          false, false);
      }
    }

    return new ReporterDetails();
  }

  /*
   * mets/amdSec/digiprovMD/@ID An xml:id identifier for the digital provenance
   * metadata section mets/amdSec/digiprovMD used for internal package references.
   * It must be unique within the package.
   */
  private ReporterDetails validateCSIP33() {
    for (AmdSecType a : amdSec) {
      List<MdSecType> digiprov = a.getDigiprovMD();
      for (MdSecType md : digiprov) {
        if (!checkId(md.getID())) {
          addId(md.getID());
        } else {
          StringBuilder message = new StringBuilder();
          message.append("Value ").append(md.getID())
            .append(" in %1$s for mets/amdSec/digiprovMD/@ID isn't unique in the package");
          return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
            Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/digiprovMD/@STATUS Indicates the status of the package using a
   * fixed vocabulary.See also: dmdSec status
   */
  private ReporterDetails validateCSIP34() {
    for (AmdSecType a : amdSec) {
      List<MdSecType> digiprov = a.getDigiprovMD();
      for (MdSecType md : digiprov) {
        String status = md.getSTATUS();
        if (!dmdSecStatus.contains(status)) {
          StringBuilder message = new StringBuilder();
          message.append("Value ").append(status).append(" in %1$s for mets/amdSec/digiprovMD/@STATUS isn't valid");
          return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
            Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
        }
      }
    }
    return new ReporterDetails();

  }

  /*
   * mets/amdSec/digiprovMD/mdRef Reference to the digital provenance metadata
   * file stored in the “metadata” section of the IP.
   */
  private ReporterDetails validateCSIP35() {
    for (AmdSecType a : amdSec) {
      List<MdSecType> digiprov = a.getDigiprovMD();
      for (MdSecType md : digiprov) {
        MdSecType.MdRef mdRef = md.getMdRef();
        if (mdRef == null) {
          return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
            Message.createErrorMessage(
              "mets/amdSec/digiprovMD/mdRef in %1$s is the reference to the digital provenance metadata file", metsName,
              isRootMets()),
            false, false);
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/digiprovMD/mdRef[@LOCTYPE=’URL’] The locator type is always used
   * with the value “URL” from the vocabulary in the attribute. Ver este tb
   */
  private ReporterDetails validateCSIP36() {
    for (AmdSecType a : amdSec) {
      List<MdSecType> digiprov = a.getDigiprovMD();
      for (MdSecType md : digiprov) {
        MdSecType.MdRef mdRef = md.getMdRef();
        if (mdRef != null) {
          String loctype = mdRef.getLOCTYPE();
          if (loctype != null) {
            if (!loctype.equals("URL")) {
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                Message.createErrorMessage("mets/amdSec/digiprovMD/mdRef[@LOCTYPE='URL'] value in %1$s must be URL",
                  metsName, isRootMets()),
                false, false);
            }
          } else {
            return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
              Message.createErrorMessage("mets/amdSec/digiprovMD/mdRef[@LOCTYPE='URL'] in %1$s can't be null", metsName,
                isRootMets()),
              false, false);
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/digiprovMD/mdRef[@xlink:type=’simple’] Attribute used with the
   * value “simple”. Value list is maintained by the xlink standard.
   */
  private ReporterDetails validateCSIP37() throws IOException {
    HashMap<String, String> amdSecTypes = new HashMap<>();
    MetsHandler amdSecHandler = new MetsHandler("digiprovMD", "mdRef", amdSecTypes);
    MetsParser metsParser = new MetsParser();
    InputStream metsStream = null;
    if (!amdSec.isEmpty()) {
      if (isZipFileFlag()) {
        if (isRootMets()) {
          metsStream = zipManager.getMetsRootInputStream(getEARKSIPpath());
        } else {
          metsStream = zipManager.getZipInputStream(getEARKSIPpath(), metsPath + "METS.xml");
        }
      } else {
        if (isRootMets()) {
          metsStream = folderManager.getMetsRootInputStream(getEARKSIPpath());
        } else {
          metsStream = folderManager.getInputStream(Paths.get(metsPath).resolve("METS.xml"));
        }
      }
    }
    if (metsStream != null) {
      metsParser.parse(amdSecHandler, metsStream);
    }
    ReporterDetails details = new ReporterDetails();
    for (AmdSecType a : amdSec) {
      List<MdSecType> digiprov = a.getDigiprovMD();
      for (MdSecType md : digiprov) {
        MdSecType.MdRef mdRef = md.getMdRef();

        if (amdSecTypes.get(mdRef.getID()) == null) {
          details.setValid(false);
          details.addIssue(Message.createErrorMessage(
            "mets/amdSec/digiprovMD/mdRef[@xlink:type=’simple’] in %1$s can't be null", metsName, isRootMets()));
        } else {
          if (!amdSecTypes.get(mdRef.getID()).equals("simple")) {
            details.setValid(false);
            details.addIssue(Message.createErrorMessage(
              "mets/amdSec/digiprovMD/mdRef[@xlink:type=’simple’] value in %1$s must be 'simple'", metsName,
              isRootMets()));
          }
        }
      }
    }
    return details;
  }

  /*
   * mets/amdSec/digiprovMD/mdRef/@xlink:href The actual location of the resource.
   * This specification recommends recording a URL type filepath within this
   * attribute.
   */
  private ReporterDetails validateCSIP38() throws IOException {
    for (AmdSecType a : amdSec) {
      List<MdSecType> digiprov = a.getDigiprovMD();
      for (MdSecType md : digiprov) {
        MdSecType.MdRef mdRef = md.getMdRef();
        StringBuilder message = new StringBuilder();
        if (mdRef != null) {
          String href = URLDecoder.decode(mdRef.getHref(), UTF_8);
          if (isZipFileFlag()) {
            StringBuilder path = new StringBuilder();
            if (isRootMets()) {
              if (mets.getOBJID() != null) {
                path.append(mets.getOBJID()).append("/").append(href);
              } else {
                return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                  Message.createErrorMessage("mets/OBJECTID in %1$s can't be null", metsName, isRootMets()), false,
                  false);
              }
            } else {
              path.append(metsPath).append(href);
            }
            if (!zipManager.checkPathExists(getEARKSIPpath(), path.toString())) {
              message.append("mets/amdSec/digiprovMD/mdRef/@xlink:href (").append(path.toString())
                .append(") doesn't exists (%1$s)");
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
            }
          } else {
            if (!folderManager.checkPathExists(Paths.get(metsPath).resolve(href))) {
              message.append("mets/amdSec/digiprovMD/mdRef/@xlink:href (").append(Paths.get(metsPath).resolve(href))
                .append(") doesn't exists (%1$s)");
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, message.toString(), false,
                false);
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/digiprovMD/mdRef/@MDTYPE Specifies the type of metadata in the
   * referenced file. Values are taken from the list provided by the METS.
   */
  private ReporterDetails validateCSIP39() {
    List<String> tmp = new ArrayList<>();
    for (MetadataType md : MetadataType.values()) {
      tmp.add(md.toString());
    }
    for (AmdSecType a : amdSec) {
      List<MdSecType> digiprov = a.getDigiprovMD();
      for (MdSecType md : digiprov) {
        MdSecType.MdRef mdRef = md.getMdRef();
        if (mdRef != null) {
          String mdType = mdRef.getMDTYPE();
          if (mdType == null) {
            return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, Message.createErrorMessage(
              "mets/amdSec/digiprovMD/mdRef/@MDTYPE in %1$s can't be null", metsName, isRootMets()), false, false);
          } else {
            if (!tmp.contains(mdType)) {
              StringBuilder message = new StringBuilder();
              message.append("Value ").append(mdType)
                .append(" for mets/amdSec/digiprovMD/mdRef/@MDTYPE value in %1$s isn't valid");
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/digiprovMD/mdRef/@MIMETYPE The IANA mime type for the referenced
   * file.See also: IANA media types
   */
  private ReporterDetails validateCSIP40() {
    for (AmdSecType amdSecType : amdSec) {
      List<MdSecType> digiprovMD = amdSecType.getDigiprovMD();
      for (MdSecType digiprov : digiprovMD) {
        MdSecType.MdRef mdRef = digiprov.getMdRef();
        String mimeType = mdRef.getMIMETYPE();
        if (mimeType != null) {
          if (!ianaMediaTypes.contains(mimeType)) {
            StringBuilder message = new StringBuilder();
            message.append("Value ").append(mimeType)
              .append(" in %1$s for mets/amdSec/digiprovMD/mdRef/@MIMETYPE isn't valid");
            return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
              Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
          }
        } else {
          return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
            Message.createErrorMessage("mets/amdSec/digiprovMD/mdRef/@MIMETYPE of file in %1$s can't be null", metsName,
              isRootMets()),
            false, false);
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/digiprovMD/mdRef/@SIZE Size of the referenced file in bytes.
   */
  private ReporterDetails validateCSIP41() throws IOException {
    for (AmdSecType amdSecType : amdSec) {
      List<MdSecType> digiprov = amdSecType.getDigiprovMD();
      for (MdSecType md : digiprov) {
        MdSecType.MdRef mdRef = md.getMdRef();
        if (mdRef != null) {
          String href = URLDecoder.decode(mdRef.getHref(), UTF_8);
          Long size = mdRef.getSIZE();
          if (size == null) {
            return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
              Message.createErrorMessage("mets/amdSec/digiprovMD/mdRef/@SIZE of file in %1$s can't be null", metsName,
                isRootMets()),
              false, false);
          } else {
            StringBuilder message = new StringBuilder();
            if (isZipFileFlag()) {
              String file;
              if (isRootMets()) {
                String OBJECTID = mets.getOBJID();
                if (OBJECTID != null) {
                  file = OBJECTID + "/" + href;
                } else {
                  return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                    Message.createErrorMessage(METS_OBJECTID_CAN_T_BE_NULL, metsName, isRootMets()), false, false);
                }
              } else {
                file = metsPath + href;
              }
              if (!zipManager.verifySize(getEARKSIPpath(), file, size)) {
                message.append("mets/dmdSec/mdRef/@SIZE ").append(size).append(" in %1$s and size of file (")
                  .append(file).append(") isn't equal");
                return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                  Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
              }
            } else {
              if (isRootMets()) {
                if (!folderManager.verifySize(getEARKSIPpath().resolve(href), size)) {
                  message.append("mets/dmdSec/mdRef/@SIZE ").append(size).append(" in %1$s and size of file (")
                    .append(getEARKSIPpath().resolve(href)).append(") isn't equal");
                  return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                    Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
                }
              } else {
                if (!folderManager.verifySize(Paths.get(metsPath).resolve(href), size)) {
                  message.append("mets/dmdSec/mdRef/@SIZE ").append(size).append(" in %1$s and size of file (")
                    .append(Paths.get(metsPath).resolve(href)).append(") isn't equal");
                  return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                    Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
                }
              }
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/digiprovMD/mdRef/@CREATED Creation date of the referenced file.
   */
  private ReporterDetails validateCSIP42() {
    for (AmdSecType a : amdSec) {
      List<MdSecType> digiprov = a.getDigiprovMD();
      for (MdSecType md : digiprov) {
        MdSecType.MdRef mdRef = md.getMdRef();
        if (mdRef.getCREATED() == null) {
          return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, Message.createErrorMessage(
            "mets/amdSec/digiprovMD/mdRef/@CREATED in %1$s can't be null", metsName, isRootMets()), false, false);
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/digiprovMD/mdRef/@CHECKSUM The checksum of the referenced file.
   */
  private ReporterDetails validateCSIP43() throws IOException, NoSuchAlgorithmException {
    List<String> tmp = new ArrayList<>();
    for (CHECKSUMTYPE check : CHECKSUMTYPE.values()) {
      tmp.add(check.toString());
    }
    for (AmdSecType a : amdSec) {
      List<MdSecType> digiprov = a.getDigiprovMD();
      for (MdSecType md : digiprov) {
        MdSecType.MdRef mdRef = md.getMdRef();
        String checksumType = mdRef.getCHECKSUMTYPE();
        if (checksumType == null) {
          return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, Message.createErrorMessage(
            "mets/amdSec/digiprovMD/mdRef/@CHECKSUMTYPE in %1$s can't be null", metsName, isRootMets()), false, false);
        } else {
          if (!tmp.contains(checksumType)) {
            return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
              Message.createErrorMessage("mets/amdSec/digiprovMD/mdRef/@CHECKSUMTYPE value in %1$s isn't valid",
                metsName, isRootMets()),
              false, false);
          } else {
            String checksum = mdRef.getCHECKSUM();
            if (checksum == null) {
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, Message.createErrorMessage(
                "mets/amdSec/digiprovMD/mdRef/@CHECKSUM in %1$s can't be null", metsName, isRootMets()), false, false);
            } else {
              String href = URLDecoder.decode(mdRef.getHref(), UTF_8);
              StringBuilder message = new StringBuilder();
              if (isZipFileFlag()) {
                StringBuilder file = new StringBuilder();
                if (isRootMets()) {
                  String OBJECTID = mets.getOBJID();
                  if (OBJECTID != null) {
                    file.append(OBJECTID).append("/").append(href);
                  } else {
                    return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                      Message.createErrorMessage(METS_OBJECTID_CAN_T_BE_NULL, metsName, isRootMets()), false, false);
                  }
                } else {
                  file.append(metsPath).append("/").append(href);
                }
                if (!zipManager.verifyChecksum(getEARKSIPpath(), file.toString(), checksumType, checksum)) {
                  message.append("mets/dmdSec/mdRef/@CHECKSUM ").append(checksum).append(" in %1$s and size of file (")
                    .append(file).append(") isn't equal");
                  return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                    Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
                }
              } else {
                if (!folderManager.verifyChecksum(Paths.get(metsPath).resolve(href), checksumType, checksum)) {
                  message.append("mets/dmdSec/mdRef/@CHECKSUM ").append(checksum).append(" in %1$s and size of file (")
                    .append(Paths.get(metsPath).resolve(href)).append(") isn't equal");
                  return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                    Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
                }
              }
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/digiprovMD/mdRef/@CHECKSUMTYPE The type of checksum following the
   * value list present in the METS-standard which has been used for calculating
   * the checksum for the referenced file.
   */
  private ReporterDetails validateCSIP44() {
    List<String> tmp = new ArrayList<>();
    for (CHECKSUMTYPE check : CHECKSUMTYPE.values()) {
      tmp.add(check.toString());
    }
    for (AmdSecType a : amdSec) {
      List<MdSecType> digiprov = a.getDigiprovMD();
      for (MdSecType md : digiprov) {
        MdSecType.MdRef mdRef = md.getMdRef();
        String checksumType = mdRef.getCHECKSUMTYPE();
        if (checksumType == null) {

          return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, Message.createErrorMessage(
            "mets/amdSec/digiprovMD/mdRef/@CHECKSUMTYPE in %1$s can't be null", metsName, isRootMets()), false, false);
        } else {
          if (!tmp.contains(checksumType)) {
            StringBuilder message = new StringBuilder();
            message.append("Value ").append(checksumType)
              .append(" for mets/amdSec/digiprovMD/mdRef/@CHECKSUMTYPE value in %1$s isn't valid");
            return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
              Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/rightsMD A simple rights statement may be used to describe
   * general permissions for the package. Individual representations should state
   * their specific rights in their representation METS file. Available standards
   * include RightsStatements.org , Europeana rights statements info , METS Rights
   * Schema created and maintained by the METS Board, the rights part of PREMIS as
   * well as own local rights statements in use.
   */
  private ReporterDetails validateCSIP45() {
    boolean found = false;
    for (AmdSecType a : amdSec) {
      if (a.getRightsMD() != null && !a.getRightsMD().isEmpty()) {
        found = true;
      }
    }
    if (found) {
      return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
        Message.createErrorMessage("You have specified mets/amdSec/rightsMD in %1$s", metsName, isRootMets()), true,
        false);
    }
    return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
      Message.createErrorMessage(
        "Individual representations should state their specific rights in their representation METS file (%1$s)",
        metsName, isRootMets()),
      false, false);
  }

  /*
   * mets/amdSec/rightsMD/@ID An xml:id identifier for the rights metadata section
   * ( <rightsMD> ) used for internal package references. It must be unique within
   * the package.
   */
  private ReporterDetails validateCSIP46() {
    for (AmdSecType a : amdSec) {
      List<MdSecType> rigthsMD = a.getRightsMD();
      if (rigthsMD != null && !rigthsMD.isEmpty()) {
        for (MdSecType rmd : rigthsMD) {
          if (checkId(rmd.getID())) {
            StringBuilder message = new StringBuilder();
            message.append("Value ").append(rmd.getID())
              .append(" in %1$s for mets/amdSec/rightsMD/@ID isn't unique in the package");
            return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
              Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
          } else {
            addId(rmd.getID());
          }
        }
      } else {
        return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
          Message.createErrorMessage("Skipped in %1$s doesn't have mets/amdSec/rightsMD", metsName, isRootMets()), true,
          true);
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/rightsMD/@STATUS Indicates the status of the package using a
   * fixed vocabulary.See also: dmdSec status
   */
  private ReporterDetails validateCSIP47() {
    for (AmdSecType a : amdSec) {
      List<MdSecType> rigthsMD = a.getRightsMD();
      if (rigthsMD != null && !rigthsMD.isEmpty()) {
        for (MdSecType rmd : rigthsMD) {
          String status = rmd.getSTATUS();
          if (status == null) {
            return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
              Message.createErrorMessage("mets/amdSec/rightsMD/@STATUS in %1$s can't be null", metsName, isRootMets()),
              false, false);
          } else {
            if (!dmdSecStatus.contains(status)) {
              StringBuilder message = new StringBuilder();
              message.append("Value ").append(status)
                .append(" in %1$s for mets/amdSec/rightsMD/@STATUS value isn't valid");
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, message.toString(), false,
                false);
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/rightsMD/mdRef Reference to the rights metadata file stored in
   * the “metadata” section of the IP.
   */
  private ReporterDetails validateCSIP48() {
    for (AmdSecType a : amdSec) {
      List<MdSecType> rigthsMD = a.getRightsMD();
      if (rigthsMD != null && !rigthsMD.isEmpty()) {
        for (MdSecType rmd : rigthsMD) {
          if (rmd.getMdRef() == null) {
            return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
              Message.createErrorMessage("mets/amdSec/rightsMD/mdRef in %1$s is null", metsName, isRootMets()), false,
              false);
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/rightsMD/mdRef[@LOCTYPE=’URL’] The locator type is always used
   * with the value “URL” from the vocabulary in the attribute.
   */
  private ReporterDetails validateCSIP49() {
    for (AmdSecType a : amdSec) {
      List<MdSecType> rigthsMD = a.getRightsMD();
      if (rigthsMD != null && !rigthsMD.isEmpty()) {
        for (MdSecType rmd : rigthsMD) {
          MdSecType.MdRef mdRef = rmd.getMdRef();
          if (mdRef != null) {
            String loctype = mdRef.getLOCTYPE();
            if (loctype == null) {
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                Message.createErrorMessage("mets/amdSec/rightsMD/mdRef[@LOCTYPE=’URL’] in %1$s can't be null", metsName,
                  isRootMets()),
                false, false);
            } else {
              if (!loctype.equals("URL")) {
                return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                  Message.createErrorMessage("mets/amdSec/rightsMD/mdRef[@LOCTYPE=’URL’] value in %1$s must be 'URL'",
                    metsName, isRootMets()),
                  false, false);
              }
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/rightsMD/mdRef[@xlink:type=’simple’] Attribute used with the
   * value “simple”. Value list is maintained by the xlink standard.
   */
  private ReporterDetails validateCSIP50() throws IOException {
    HashMap<String, String> amdSecTypes = new HashMap<>();
    MetsHandler amdSecHandler = new MetsHandler("rightsMD", "mdRef", amdSecTypes);
    MetsParser metsParser = new MetsParser();
    InputStream metsStream = null;
    if (!amdSec.isEmpty()) {
      if (isZipFileFlag()) {
        if (isRootMets()) {
          metsStream = zipManager.getMetsRootInputStream(getEARKSIPpath());
        } else {
          metsStream = zipManager.getZipInputStream(getEARKSIPpath(), metsPath + "METS.xml");
        }
      } else {
        if (isRootMets()) {
          metsStream = folderManager.getMetsRootInputStream(getEARKSIPpath());
        } else {
          metsStream = folderManager.getInputStream(Paths.get(metsPath).resolve("METS.xml"));
        }
      }
    }
    if (metsStream != null) {
      metsParser.parse(amdSecHandler, metsStream);
    }
    for (AmdSecType a : amdSec) {
      List<MdSecType> rigthsMD = a.getRightsMD();
      if (rigthsMD != null && !rigthsMD.isEmpty()) {
        for (MdSecType rmd : rigthsMD) {
          MdSecType.MdRef mdRef = rmd.getMdRef();
          if (mdRef != null) {
            if (amdSecTypes.get(mdRef.getID()) == null) {
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                Message.createErrorMessage("mets/amdSec/rightsMD/mdRef[@xlink:type=’simple’] in %1$s can't be null ",
                  metsName, isRootMets()),
                false, false);
            } else {
              if (!amdSecTypes.get(mdRef.getID()).equals("simple")) {
                return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                  Message.createErrorMessage(
                    "mets/amdSec/rightsMD/mdRef[@xlink:type=’simple’] value in %1$s isn't 'simple'", metsName,
                    isRootMets()),
                  false, false);
              }
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/rightsMD/mdRef/@xlink:href The actual location of the resource.
   * We recommend recording a URL type filepath within this attribute.
   */
  private ReporterDetails validateCSIP51() throws IOException {
    for (AmdSecType a : amdSec) {
      List<MdSecType> rigthsMD = a.getRightsMD();
      if (rigthsMD != null && !rigthsMD.isEmpty()) {
        for (MdSecType rmd : rigthsMD) {
          MdSecType.MdRef mdRef = rmd.getMdRef();
          if (mdRef != null) {
            String href = URLDecoder.decode(mdRef.getHref(), UTF_8);
            StringBuilder message = new StringBuilder();
            if (isZipFileFlag()) {
              StringBuilder filePath = new StringBuilder();
              if (isRootMets()) {
                filePath.append(mets.getOBJID()).append("/").append(href);
              } else {
                filePath.append(metsPath).append(href);
              }
              if (!zipManager.checkPathExists(getEARKSIPpath(), filePath.toString())) {
                message.append("mets/amdSec/rightsMD/mdRef/@xlink:href ").append(filePath)
                  .append(" doesn't exists (in %1$s)");
                return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                  Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
              }
            } else {
              if (!folderManager.checkPathExists(Paths.get(metsPath).resolve(href))) {
                message.append("mets/amdSec/rightsMD/mdRef/@xlink:href ").append(Paths.get(metsPath).resolve(href))
                  .append(" doesn't exists (in %1$s)");
                return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                  Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
              }
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/rightsMD/mdRef/@MDTYPE Specifies the type of metadata in the
   * referenced file. Value is taken from the list provided by the METS.
   */
  private ReporterDetails validateCSIP52() {
    List<String> tmp = new ArrayList<>();
    for (MetadataType md : MetadataType.values()) {
      tmp.add(md.toString());
    }
    for (AmdSecType a : amdSec) {
      List<MdSecType> rigthsMD = a.getRightsMD();
      if (rigthsMD != null && !rigthsMD.isEmpty()) {
        for (MdSecType rmd : rigthsMD) {
          MdSecType.MdRef mdRef = rmd.getMdRef();
          if (mdRef != null) {
            String mdType = mdRef.getMDTYPE();
            if (mdType == null) {
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, Message.createErrorMessage(
                "mets/amdSec/rightsMD/mdRef/@MDTYPE in %1$s can't be null", metsName, isRootMets()), false, false);
            } else {
              if (!tmp.contains(mdType)) {
                StringBuilder message = new StringBuilder();
                message.append("Value ").append(mdType)
                  .append(" in %1$s for mets/amdSec/rightsMD/mdRef/@MDTYPE value isn't valid");
                return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                  Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
              }
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/rightsMD/mdRef/@MIMETYPE The IANA mime type for the referenced
   * file.See also: IANA media types
   */
  private ReporterDetails validateCSIP53() {
    for (AmdSecType a : amdSec) {
      List<MdSecType> rigthsMD = a.getRightsMD();
      if (rigthsMD != null && !rigthsMD.isEmpty()) {
        for (MdSecType rmd : rigthsMD) {
          MdSecType.MdRef mdRef = rmd.getMdRef();
          if (mdRef != null) {
            String mimeType = mdRef.getMIMETYPE();
            if (mimeType == null) {
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, Message.createErrorMessage(
                "mets/amdSec/rightsMD/mdRef/@MIMETYPE in %1$s can't be null", metsName, isRootMets()), false, false);
            } else {
              if (!ianaMediaTypes.contains(mimeType)) {
                StringBuilder message = new StringBuilder();
                message.append("Value ").append(mimeType)
                  .append(" in %1$s for mets/amdSec/rightsMD/mdRef/@MIMETYPE value isn't valid");
                return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                  Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
              }
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/rightsMD/mdRef/@SIZE Size of the referenced file in bytes.
   */
  private ReporterDetails validateCSIP54() throws IOException {
    for (AmdSecType a : amdSec) {
      List<MdSecType> rigthsMD = a.getRightsMD();
      if (rigthsMD != null && !rigthsMD.isEmpty()) {
        for (MdSecType rmd : rigthsMD) {
          MdSecType.MdRef mdRef = rmd.getMdRef();
          if (mdRef != null) {
            String href = URLDecoder.decode(mdRef.getHref(), UTF_8);
            Long size = mdRef.getSIZE();
            if (size == null) {
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, Message.createErrorMessage(
                "mets/amdSec/rightsMD/mdRef/@SIZE in %1$s can't be null", metsName, isRootMets()), false, false);
            } else {
              StringBuilder message = new StringBuilder();
              if (isZipFileFlag()) {
                StringBuilder filePath = new StringBuilder();
                if (isRootMets()) {
                  filePath.append(mets.getOBJID()).append("/").append(href);
                } else {
                  filePath.append(metsPath).append(href);
                }
                if (!zipManager.verifySize(getEARKSIPpath(), filePath.toString(), size)) {
                  message.append("mets/dmdSec/mdRef/@SIZE ").append(size).append(" in %1$s and size of file (")
                    .append(filePath).append(") isn't equal");
                  return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                    Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
                }
              } else {
                if (isRootMets()) {
                  if (!folderManager.verifySize(getEARKSIPpath().resolve(href), size)) {
                    message.append("mets/dmdSec/mdRef/@SIZE ").append(size).append(" in %1$s and size of file (")
                      .append(getEARKSIPpath().resolve(href)).append(") isn't equal");
                    return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                      Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
                  }
                } else {
                  if (!folderManager.verifySize(Paths.get(metsPath).resolve(href), size)) {
                    message.append("mets/dmdSec/mdRef/@SIZE ").append(size).append(" in %1$s and size of file (")
                      .append(Paths.get(metsPath).resolve(href)).append(") isn't equal");
                    return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                      Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
                  }
                }
              }
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/rightsMD/mdRef/@CREATED Creation date of the referenced file.
   */
  private ReporterDetails validateCSIP55() {
    for (AmdSecType a : amdSec) {
      List<MdSecType> rigthsMD = a.getRightsMD();
      if (rigthsMD != null && !rigthsMD.isEmpty()) {
        for (MdSecType rmd : rigthsMD) {
          MdSecType.MdRef mdRef = rmd.getMdRef();
          if (mdRef.getCREATED() == null) {
            return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, Message.createErrorMessage(
              "mets/amdSec/rightsMD/mdRef/@CREATED in %1$s can't be null", metsName, isRootMets()), false, false);
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/rightsMD/mdRef/@CHECKSUM The checksum of the referenced file.
   */
  private ReporterDetails validateCSIP56() throws IOException, NoSuchAlgorithmException {
    List<String> tmp = new ArrayList<>();
    for (CHECKSUMTYPE check : CHECKSUMTYPE.values()) {
      tmp.add(check.toString());
    }
    for (AmdSecType a : amdSec) {
      List<MdSecType> rigthsMD = a.getRightsMD();
      if (rigthsMD != null && !rigthsMD.isEmpty()) {
        for (MdSecType rmd : rigthsMD) {
          MdSecType.MdRef mdRef = rmd.getMdRef();
          String checksumType = mdRef.getCHECKSUMTYPE();
          if (checksumType == null) {
            return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, Message.createErrorMessage(
              "mets/amdSec/rightsMD/mdRef/@CHECKSUMTYPE in %1$s can't be null", metsName, isRootMets()), false, false);
          } else {
            if (!tmp.contains(checksumType)) {
              StringBuilder message = new StringBuilder();
              message.append("Value ").append(checksumType)
                .append(" for mets/amdSec/rightsMD/mdRef/@CHECKSUMTYPE value in %1$s isn't valid");
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
            } else {
              String checksum = mdRef.getCHECKSUM();
              if (checksum == null) {
                return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, Message.createErrorMessage(
                  "mets/amdSec/rightsMD/mdRef/@CHECKSUM in %1$s can't be null", metsName, isRootMets()), false, false);
              } else {
                String href = URLDecoder.decode(mdRef.getHref(), UTF_8);
                StringBuilder message = new StringBuilder();
                if (isZipFileFlag()) {
                  StringBuilder filePath = new StringBuilder();
                  if (isRootMets()) {
                    filePath.append(mets.getOBJID()).append("/").append(href);
                  } else {
                    filePath.append(metsPath).append(href);
                  }
                  if (!zipManager.verifyChecksum(getEARKSIPpath(), filePath.toString(), checksumType, checksum)) {
                    message.append("mets/dmdSec/mdRef/@CHECKSUM ").append(checksum).append(" and checksum of file (")
                      .append(Paths.get(metsPath).resolve(filePath.toString())).append(") isn't equal (%1$s)");
                    return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                      Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
                  }
                } else {
                  if (!folderManager.verifyChecksum(Paths.get(metsPath).resolve(href), checksumType, checksum)) {
                    message.append("mets/dmdSec/mdRef/@CHECKSUM ").append(checksum).append(" and checksum of file (")
                      .append(Paths.get(metsPath).resolve(Paths.get(metsPath).resolve(href)))
                      .append(") isn't equal (in %1$s)");
                    return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                      Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
                  }
                }
              }
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }

  /*
   * mets/amdSec/rightsMD/mdRef/@CHECKSUMTYPE The type of checksum following the
   * value list present in the METS-standard which has been used for calculating
   * the checksum for the referenced file.
   */
  private ReporterDetails validateCSIP57() {
    List<String> tmp = new ArrayList<>();
    for (CHECKSUMTYPE check : CHECKSUMTYPE.values()) {
      tmp.add(check.toString());
    }
    for (AmdSecType a : amdSec) {
      List<MdSecType> rigthsMD = a.getRightsMD();
      if (rigthsMD != null && !rigthsMD.isEmpty()) {
        for (MdSecType rmd : rigthsMD) {
          MdSecType.MdRef mdRef = rmd.getMdRef();
          String checksumType = mdRef.getCHECKSUMTYPE();
          if (checksumType == null) {
            return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION, Message.createErrorMessage(
              "mets/amdSec/rightsMD/mdRef/@CHECKSUMTYPE in %1$s can't be null", metsName, isRootMets()), false, false);
          } else {
            if (!tmp.contains(checksumType)) {
              StringBuilder message = new StringBuilder();
              message.append("Value ").append(checksumType)
                .append(" in %1$s for mets/amdSec/rightsMD/mdRef/@CHECKSUMTYPE isn't valid");
              return new ReporterDetails(Constants.VALIDATION_REPORT_HEADER_CSIP_VERSION,
                Message.createErrorMessage(message.toString(), metsName, isRootMets()), false, false);
            }
          }
        }
      }
    }
    return new ReporterDetails();
  }
}
