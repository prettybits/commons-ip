/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE file at the root of the source
 * tree and available online at
 *
 * https://github.com/keeps/commons-ip
 */
package org.roda_project.commons_ip.model;

import java.io.Serializable;

import org.roda_project.commons_ip.utils.Utils;

public class IPMetadata implements Serializable {
  private static final long serialVersionUID = 3697420963728707487L;

  private String id;
  private IPFile metadata;
  private MetadataType metadataType;

  public IPMetadata() {
    // empty constructor for serialization purposes
  }

  public IPMetadata(IPFile metadata) {
    id = Utils.generateRandomAndPrefixedUUID();
    this.metadata = metadata;
    metadataType = MetadataType.OTHER();
  }

  public IPMetadata(IPFile metadata, MetadataType metadataType) {
    this.id = Utils.generateRandomAndPrefixedUUID();
    this.metadata = metadata;
    this.metadataType = metadataType;
  }

  public IPMetadata(String id, IPFile metadata, MetadataType metadataType) {
    this.id = id;
    this.metadata = metadata;
    this.metadataType = metadataType;
  }

  public String getId() {
    return id;
  }

  public IPMetadata setId(String id) {
    this.id = id;
    return this;
  }

  public IPFile getMetadata() {
    return metadata;
  }

  public IPMetadata setMetadata(IPFile metadata) {
    this.metadata = metadata;
    return this;
  }

  public MetadataType getMetadataType() {
    return metadataType;
  }

  public IPMetadata setMetadataType(MetadataType metadataType) {
    this.metadataType = metadataType;
    return this;
  }

  @Override
  public String toString() {
    return "IPMetadata [id=" + id + ", metadata=" + metadata + ", metadataType=" + metadataType + "]";
  }

}
