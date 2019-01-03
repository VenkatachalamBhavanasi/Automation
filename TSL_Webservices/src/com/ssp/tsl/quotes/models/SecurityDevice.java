package com.ssp.tsl.quotes.models;

public class SecurityDevice {
	private boolean bS6803Install;
    private String certificateNumber;
    private boolean certificateSeen;
    private String dateFitted;
    private Integer id;
    private String installedBy;
    private String installedByDescription;
    private String makeAndModel;
    private String makeAndModelDescription;
	public boolean isbS6803Install() {
		return bS6803Install;
	}
	public void setbS6803Install(boolean bS6803Install) {
		this.bS6803Install = bS6803Install;
	}
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	public boolean isCertificateSeen() {
		return certificateSeen;
	}
	public void setCertificateSeen(boolean certificateSeen) {
		this.certificateSeen = certificateSeen;
	}
	public String getDateFitted() {
		return dateFitted;
	}
	public void setDateFitted(String dateFitted) {
		this.dateFitted = dateFitted;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInstalledBy() {
		return installedBy;
	}
	public void setInstalledBy(String installedBy) {
		this.installedBy = installedBy;
	}
	public String getInstalledByDescription() {
		return installedByDescription;
	}
	public void setInstalledByDescription(String installedByDescription) {
		this.installedByDescription = installedByDescription;
	}
	public String getMakeAndModel() {
		return makeAndModel;
	}
	public void setMakeAndModel(String makeAndModel) {
		this.makeAndModel = makeAndModel;
	}
	public String getMakeAndModelDescription() {
		return makeAndModelDescription;
	}
	public void setMakeAndModelDescription(String makeAndModelDescription) {
		this.makeAndModelDescription = makeAndModelDescription;
	}

}
