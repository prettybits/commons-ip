# E-ARK IP validation and manipulation tool and library

This project provides a command-line interface and Java library to validate and manipulate OAIS Information Packages of
different formats: E-ARK (version 1 & 2 in alpha stage), BagIt, Hungarian type 4 SIP.

The E-ARK Information Packages are maintained by the Digital Information LifeCycle Interoperability Standards Board (
DILCIS Board). DILCIS Board is an international group of experts committed to maintain and sustain maintain a set of
interoperability specifications which allow for the transfer, long-term preservation, and reuse of digital information
regardless of the origin or type of the information.

More specifically, the DILCIS Board maintains specifications initially developed within the E-ARK Project (02.2014 -
01.2017):

- Common Specification for Information Packages
- E-ARK Submission Information Package (SIP)
- E-ARK Archival Information Package (AIP)
- E-ARK Dissemination Information Package (DIP)

The DILCIS Board collaborates closely with the Swiss Federal Archives in regard to the maintenance of the SIARD (
Software Independent Archiving of Relational Databases) specification.

For more information about the E-ARK Information Packages specifications, please visit http://www.dilcis.eu/

## Installation

### Requirements

* Java (>= 1.8)

Download the [latest release](https://github.com/keeps/commons-ip/releases/latest) to use as a tool or check below how
to use it as a Java Library.

## Usage

You can use the commons-ip as a command-line tool or a Java library.

### Use as a command-line tool

To use commons-ip command-line tool, need to download
the [latest release](https://github.com/keeps/commons-ip/releases/latest). This tool can validate a SIP or create a
valid EARK2 SIP.

To validate a SIP have to use the following options:

* **validate**, [REQUIRED] this option is for the CLI to know that is to perform the validation of a SIP.
* **-i**, [REQU,,,IRED] Path(s) to the SIP(s) archive file or folder(s).
* **-o,** [OPTIONAL]  Path to the Directory where you want to save the validation report.
* **-r,** [OPTIONAL] The type of report (Valid Option eark or default)
* **-v,** [OPTIONAL] Verbose option (Will print all validation steps)

To create a EARK-2 SIP have to use the following options:

* **create**, [REQUIRED] this option is for the CLI to know that is to perform the creation of a EARK-2 SIP.
* **-mf** or **--metadata-file**, [OPTIONAL] Path to a metadata file.
* **-mt** or **--metadata-type**, [OPTIONAL] the type of the metadata.
* **-mv** or **--metadata-version**, [OPTIONAL] the version of the metadata.

**NOTE:** if does not give the metadata type and the metadata version, the tool try to obtain this values from the file
name in the following formats (file: **ead.xml** -> result: metadata type: **EAD**; file: **ead_2002.xml** -> result:
metadata type: **EAD**, metadata version: **2002**)

* **-rd** or **--representation-data**, [OPTIONAL] Path(s) folders or files to add in representation.
* **-rt** or **--representation-type**, [OPTIONAL] Type of the representation.
* **-rid** or **--representation-id**, [OPTIONAL] ID of representation.
* **-sid** or **--sip-id**, [OPTIONAL] ID of the SIP.
* **-doc** or **--documentation**, [OPTIONAL] Path(s) to folders or files to add in documentation of SIP.
* **-p** or **--path**, [OPTIONAL] Path to save the SIP.
* **-a** or **--ancestors**, [OPTIONAL] ID(s) of the ancestors of the SIP.
* **-san** or **--submitter-agent-name**, [OPTIONAL] The name of the submitter agent.
* **-aid** or **--submiter-agent-id**, [OPTIONAL] THE identification code (ID) of the submitter agent.

Examples:

###Full create SIP command with long options:

```
java -jar commons-ip-cli-2.X.Y.jar create --metadata-file metadata.xml --metadata-type ead --metadata-version 2002 \
--representation-data dataFile1.pdf dataFolder1 dataFile2.png --representation-type Mixed --representation-id representation1 \
--sip-id sip1 --ancestors sip2 sip3 --documentation documentation1 documentationFolder -p folder2 --path folder2 --submitter-agent-name agent1 --submitter-agent-id 123
```

###Full create SIP command with short options:

```
java -jar commons-ip-cli-2.X.Y.jar create -mf metadata.xml -mt ead -mv 2002 \
-rd dataFile1.pdf dataFolder1 dataFile2.png -rt Mixed -rid representation1 \
-sid sip1 -a sip2 sip3 -doc documentation1 documentationFolder -p folder2 -sn agent1 -aid 123
```

```
java -jar commons-ip-cli-2.X.Y.jar validate -i sip1.zip
```

```
java -jar commons-ip-cli-2.X.Y.jar validate -i sip1.zip sip2.zip -o output/
```

#### Output Example

The report generated by the validator is in **JSON** format and has the following structure:

```json
{
  "header": {
    "title": "Validation Report CSIP",
    "specifications": [
      {
        "id": "CSIP-2.0.4",
        "url": "https://github.com/DILCISBoard/E-ARK-CSIP/releases/tag/v2.0.4"
      },
      {
        "id": "SIP-2.0.4",
        "url": "https://github.com/DILCISBoard/E-ARK-SIP/releases/tag/v2.0.4"
      }
    ],
    "version_commons_ip": "2.0.0-alpha3-SNAPSHOT",
    "date": "2021-09-24T13:30:26.203+01:00",
    "path": "${HOME}/Full-EARK-SIP.zip"
  },
  "validation": [
    {
      "specification": "CSIP-2.0.4",
      "id": "CSIPSTR1",
      "name": "CSIP Information Package folder structure",
      "location": "",
      "description": "Any Information Package MUST be included within a  single physical root folder (known as the “Information Package root folder”). For packages presented in an archive format, see CSIPSTR3, the archive MUST unpack to a single root folder.",
      "cardinality": "",
      "level": "MUST",
      "testing": {
        "outcome": "PASSED",
        "issues": [],
        "warnings": [],
        "notes": []
      }
    },
    {
      "specification": "CSIP-2.0.4",
      "id": "CSIP32",
      "name": "Digital provenance metadata",
      "location": "mets/amdSec/digiprovMD",
      "description": "For recording information about preservation the standard PREMIS is used. It is mandatory to include one <digiprovMD> element for each piece of PREMIS metadata. The use if PREMIS in METS is following the recommendations in the 2017 version of PREMIS in METS Guidelines.",
      "cardinality": "0..n",
      "level": "SHOULD",
      "testing": {
        "outcome": "FAILED",
        "issues": [],
        "warnings": [
          "It is mandatory to include one <digiprovMD> element in Root METS.xml for each piece of PREMIS metadata"
        ],
        "notes": []
      }
    }
  ],
  "summary": {
    "success": 120,
    "warnings": 3,
    "errors": 5,
    "skipped": 33,
    "notes": 8,
    "result": "INVALID"
  }
}
```

### Use as a Java Library

* Using Maven

1. Add the following repository

  ```xml

<repository>
    <id>KEEPS-Artifacts</id>
    <name>KEEP Artifacts-releases</name>
    <url>https://artifactory.keep.pt/artifactory/keep</url>
</repository>
  ```

1. Add the following dependency

  ```xml

<dependency>
    <groupId>org.roda-project</groupId>
    <artifactId>commons-ip2</artifactId>
    <version>2.0.0-alpha1</version>
</dependency>
  ```

If using Java 11 or greater, you might need the following extra dependencies:

   ```xml

<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.0-b170201.1204</version>
</dependency>
<dependency>
<groupId>javax.activation</groupId>
<artifactId>activation</artifactId>
<version>1.1</version>
</dependency>
<dependency>
<groupId>org.glassfish.jaxb</groupId>
<artifactId>jaxb-runtime</artifactId>
<version>2.3.0-b170127.1453</version>
</dependency>
```

* Not using maven,
  download [Commons IP latest jar](https://github.com/keeps/commons-ip/releases/download/2.0.0-beta1/commons-ip-2.0.0-beta1.jar)
  , each of Commons IP dependencies (see pom.xml to know which dependencies/versions) and add them to your project
  classpath.

#### Write some code

* Create a full E-ARK SIP

```java
// 1) instantiate E-ARK SIP object
SIP sip=new EARKSIP("SIP_1",IPContentType.getMIXED(),IPContentInformationType.getMIXED());
        sip.addCreatorSoftwareAgent("RODA Commons IP","2.0.0");

// 1.1) set optional human-readable description
        sip.setDescription("A full E-ARK SIP");

// 1.2) add descriptive metadata (SIP level)
        IPDescriptiveMetadata metadataDescriptiveDC=new IPDescriptiveMetadata(
        new IPFile(Paths.get("src/test/resources/eark/metadata_descriptive_dc.xml")),
        new MetadataType(MetadataTypeEnum.DC),null);
        sip.addDescriptiveMetadata(metadataDescriptiveDC);

// 1.3) add preservation metadata (SIP level)
        IPMetadata metadataPreservation=new IPMetadata(
        new IPFile(Paths.get("src/test/resources/eark/metadata_preservation_premis.xml")));
        sip.addPreservationMetadata(metadataPreservation);

// 1.4) add other metadata (SIP level)
        IPFile metadataOtherFile=new IPFile(Paths.get("src/test/resources/eark/metadata_other.txt"));
// 1.4.1) optionally one may rename file final name
        metadataOtherFile.setRenameTo("metadata_other_renamed.txt");
        IPMetadata metadataOther=new IPMetadata(metadataOtherFile);
        sip.addOtherMetadata(metadataOther);

// 1.5) add xml schema (SIP level)
        sip.addSchema(new IPFile(Paths.get("src/test/resources/eark/schema.xsd")));

// 1.6) add documentation (SIP level)
        sip.addDocumentation(new IPFile(Paths.get("src/test/resources/eark/documentation.pdf")));

// 1.7) set optional RODA related information about ancestors
        sip.setAncestors(Arrays.asList("b6f24059-8973-4582-932d-eb0b2cb48f28"));

// 1.8) add an agent (SIP level)
        IPAgent agent=new IPAgent("Agent Name","OTHER","OTHER ROLE",CreatorType.INDIVIDUAL,"OTHER TYPE","",
        IPAgentNoteTypeEnum.SOFTWARE_VERSION);
        sip.addAgent(agent);

// 1.9) add a representation (status will be set to the default value, i.e.,
// ORIGINAL)
        IPRepresentation representation1=new IPRepresentation("representation 1");
        sip.addRepresentation(representation1);

// 1.9.1) add a file to the representation
        IPFile representationFile=new IPFile(Paths.get("src/test/resources/eark/documentation.pdf"));
        representationFile.setRenameTo("data.pdf");
        representation1.addFile(representationFile);

// 1.9.2) add a file to the representation and put it inside a folder
// called 'def' which is inside a folder called 'abc'
        IPFile representationFile2=new IPFile(Paths.get("src/test/resources/eark/documentation.pdf"));
        representationFile2.setRelativeFolders(Arrays.asList("abc","def"));
        representation1.addFile(representationFile2);

// 1.10) add a representation & define its status
        IPRepresentation representation2=new IPRepresentation("representation 2");
        representation2.setStatus(new RepresentationStatus(REPRESENTATION_STATUS_NORMALIZED));
        sip.addRepresentation(representation2);

// 1.10.1) add a file to the representation
        IPFile representationFile3=new IPFile(Paths.get("src/test/resources/eark/documentation.pdf"));
        representationFile3.setRenameTo("data3.pdf");
        representation2.addFile(representationFile3);

// 2) build SIP, providing an output directory
        Path zipSIP=sip.build(tempFolder);
```

**Note:** SIP implements the Observer Pattern. This way, if one wants to be notified of SIP build progress, one just
needs to implement SIPObserver interface and register itself in the SIP. Something like (just presenting some of the
events):

```java
public class WhoWantsToBuildSIPAndBeNotified implements SIPObserver {

  public void buildSIP() {
    ...
    SIP sip = new EARKSIP("SIP_1", IPContentType.getMIXED());
    sip.addObserver(this);
    ...
  }

  @Override
  public void sipBuildPackagingStarted(int totalNumberOfFiles) {
    ...
  }

  @Override
  public void sipBuildPackagingCurrentStatus(int numberOfFilesAlreadyProcessed) {
    ...
  }
}
```

* Parse a full E-ARK SIP

```java
// 1) invoke static method parse and that's it
SIP earkSIP=EARKSIP.parse(zipSIP);
```

## Development

In this sections are some relevant notes about Commons IP development.

### XML Beans

XML Beans are used by Commons IP to manipulate METS files using Java code.

#### Details

Some changes were made to XML Schemas in order to be able to compile XML Schemas into Java classes using XJC as well as
to be able to validate a XML file against its XML Schema without Internet connections.

The changes are:

* `src/main/resources/schemas2/mets1_12.xsd` XLink Schema location made local (and respectively file available locally)
* `src/main/resources/schemas2/mets1_12.xjb` Bindings file created to deal with attribute name conflict between METS and
  XLink Schemas

After Java classes are created, some changes were made to produce METS XML files well defined in terms of namespaces.
Namely:

* `src/main/java/org/roda_project/commons_ip2/mets_v1_12/beans/package-info.java` Annotations for corretly generate
  namespaces were added. Following is the before & then the after:

```
@javax.xml.bind.annotation.XmlSchema(namespace = "http://www.loc.gov/METS/", elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED)
```

and the after

```
@javax.xml.bind.annotation.XmlSchema(namespace = "http://www.loc.gov/METS/", elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED, xmlns = {
  @javax.xml.bind.annotation.XmlNs(prefix = "", namespaceURI = "http://www.loc.gov/METS/"),
  @javax.xml.bind.annotation.XmlNs(prefix = "xsi", namespaceURI = "http://www.w3.org/2001/XMLSchema-instance"),
  @javax.xml.bind.annotation.XmlNs(prefix = "csip", namespaceURI = "https://DILCIS.eu/XML/METS/CSIPExtensionMETS"),
  @javax.xml.bind.annotation.XmlNs(prefix = "sip", namespaceURI = "https://DILCIS.eu/XML/METS/SIPExtensionMETS"),
  @javax.xml.bind.annotation.XmlNs(prefix = "xlink", namespaceURI = "http://www.w3.org/1999/xlink")})
```

#### How to generate/update XML Beans

```
xjc -d src/main/java/ -p "org.roda_project.commons_ip2.mets_v1_12.beans" src/main/resources/schemas2/mets1_12.xsd -b src/main/resources/schemas2/mets1_12.xjb
```

### IANA Media Types

The IANA Media Types list is required to perform SIP Validation. The list is located in the folder and named as follows:

```/src/main/resources/controlledVocabularies/IANA_MEDIA_TYPES.txt```

#### How to generate/update

To update IANA media types list, in commons-ip root directory run the following command:

```./scripts/ianaMediaTypes_parser.sh```

The command executes a script that downloads all IANA Media Types (Application, Audio, Font, Image, Message, Model,
Multipart, Text, Video) from https://www.iana.org/assignments/media-types/${iana_file}.csv .
Note that this downloads different **.csv** files then creates a **.txt** file with all IANA media types appended to the
new file.

#### Extend List of IANA Media Types

The IANA media types list from https://www.iana.org/assignments/media-types/${iana_file}.csv was extended with the
following **mimetypes:**

###### Image

* image/jpeg
* image/gif

###### Text

* text/plain

###### Video

* video/mpeg

#### Update Extended list of IANA Media Types

*To add/remove new values to the list add a new line with the IANA Media Type in **scripts/ExtendedIANAMediaTypes.txt**

*Run the script again

## Commercial support

For more information or commercial support, contact [KEEP SOLUTIONS](http://www.keep.pt).

## Further reading

* [Bagit specification](https://tools.ietf.org/html/draft-kunze-bagit-13)
* [E-ARK SIP specification (all versions)](https://dilcis.eu/specifications/sip)
* [E-ARK SIP specification (latest version)](https://earksip.dilcis.eu)
* [E-ARK Common specification (all versions)](https://dilcis.eu/specifications/common-specification)
* [E-ARK Common specification (latest version)](https://earkcsip.dilcis.eu)
* [RODA-in source code](https://github.com/keeps/roda-in)
* [RODA source code](https://github.com/keeps/roda)
* [RODA Community Web site](https://www.roda-community.org)
* [E-ARK Project Web site](https://www.eark-project.com)
* [CEF eArchiving Building Block](https://ec.europa.eu/cefdigital/wiki/display/CEFDIGITAL/eArchiving)

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## Credits

- Hélder Silva (KEEP SOLUTIONS)

## License

LGPLv3
