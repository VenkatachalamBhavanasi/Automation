package com.ssp.tsl.quotes.models;

import java.util.List;

public class CarDetails {
	private Integer annualBusinessMileage;
	private Integer annualPleasureMileage;
    private String bodyType;
    private String bodyTypeDescription;
    private String classOfUse;
    private String classOfUseDescription;
    private String colour;
    private String colourDescription;
    private Integer currentValue;
    private String datePurchased;
    private Integer engineCC;
    private String finish;
    private String finishDescription;
    private String fuel;
    private String fuelDescription;
    private Integer id;
    private String leftRightHandDrive;
    private String leftRightHandDriveDescription;
    private String manufacturerAbiCode;
    private String manufacturerDescription;
    private String modelAbiCode;
    private String modelDescription;
    private List<Modification> modificationList;
    private Ncd ncd;
    private Integer numberOfDoors;
    private Integer numberOfSeats;
    private Integer odometerReading;
    private String odometerReadingDate;
    private OvernightAddress overnightAddress;	
    private String overnightLocation;
    private String overnightLocationDescription;
    private Integer pricePaid;
    private String registeredKeeper;
    private String registeredKeeperDescription;
    private String registeredOwner;
    private String registeredOwnerDescription;
    private String registeredQPlate;
    private String registration;
    private String registrationDate;
    private List<SecurityDevice> securityDeviceList;   
    private boolean selfBuilt;
    private boolean showDiscount;
    private TaxiBaseOfficeAddress taxiBaseOfficeAddress;
    private boolean taxiCommunicationDeviceFitted;
    private Integer taxiCommunicationDeviceValue;
    private String taxiDateProprietorCommenced;
    private Integer taxiFareCarryingCapacity;
    private String taxiLocalAuthorityPostcode;
    private String taxiLocalLicencingAuthority;
    private String taxiLocalLicencingAuthorityDescription;
    private Integer taxiModificationValue;
    private String taxiNcbTransferredFrom;
    private String taxiNcbTransferredFromDescription;
    private String transmission;
    private String transmissionDescription;
  
    
	public Ncd getNcd() {
		return ncd;
	}
	public void setNcd(Ncd ncd) {
		this.ncd = ncd;
	}
	public Integer getNumberOfDoors() {
		return numberOfDoors;
	}
	public void setNumberOfDoors(Integer numberOfDoors) {
		this.numberOfDoors = numberOfDoors;
	}
	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}
	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	public Integer getOdometerReading() {
		return odometerReading;
	}
	public void setOdometerReading(Integer odometerReading) {
		this.odometerReading = odometerReading;
	}
	public String getOdometerReadingDate() {
		return odometerReadingDate;
	}
	public void setOdometerReadingDate(String odometerReadingDate) {
		this.odometerReadingDate = odometerReadingDate;
	}
	public OvernightAddress getOvernightAddress() {
		return overnightAddress;
	}
	public void setOvernightAddress(OvernightAddress overnightAddress) {
		this.overnightAddress = overnightAddress;
	}
	public String getOvernightLocation() {
		return overnightLocation;
	}
	public void setOvernightLocation(String overnightLocation) {
		this.overnightLocation = overnightLocation;
	}
	public String getOvernightLocationDescription() {
		return overnightLocationDescription;
	}
	public void setOvernightLocationDescription(String overnightLocationDescription) {
		this.overnightLocationDescription = overnightLocationDescription;
	}
	public Integer getPricePaid() {
		return pricePaid;
	}
	public void setPricePaid(Integer pricePaid) {
		this.pricePaid = pricePaid;
	}
	public String getRegisteredKeeper() {
		return registeredKeeper;
	}
	public void setRegisteredKeeper(String registeredKeeper) {
		this.registeredKeeper = registeredKeeper;
	}
	public String getRegisteredKeeperDescription() {
		return registeredKeeperDescription;
	}
	public void setRegisteredKeeperDescription(String registeredKeeperDescription) {
		this.registeredKeeperDescription = registeredKeeperDescription;
	}
	public String getRegisteredOwner() {
		return registeredOwner;
	}
	public void setRegisteredOwner(String registeredOwner) {
		this.registeredOwner = registeredOwner;
	}
	public String getRegisteredOwnerDescription() {
		return registeredOwnerDescription;
	}
	public void setRegisteredOwnerDescription(String registeredOwnerDescription) {
		this.registeredOwnerDescription = registeredOwnerDescription;
	}
	public String getRegisteredQPlate() {
		return registeredQPlate;
	}
	public void setRegisteredQPlate(String registeredQPlate) {
		this.registeredQPlate = registeredQPlate;
	}
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public List<SecurityDevice> getSecurityDeviceList() {
		return securityDeviceList;
	}
	public void setSecurityDeviceList(List<SecurityDevice> securityDeviceList) {
		this.securityDeviceList = securityDeviceList;
	}
	public boolean isSelfBuilt() {
		return selfBuilt;
	}
	public void setSelfBuilt(boolean selfBuilt) {
		this.selfBuilt = selfBuilt;
	}
	public boolean isShowDiscount() {
		return showDiscount;
	}
	public void setShowDiscount(boolean showDiscount) {
		this.showDiscount = showDiscount;
	}
	public TaxiBaseOfficeAddress getTaxiBaseOfficeAddress() {
		return taxiBaseOfficeAddress;
	}
	public void setTaxiBaseOfficeAddress(TaxiBaseOfficeAddress taxiBaseOfficeAddress) {
		this.taxiBaseOfficeAddress = taxiBaseOfficeAddress;
	}
	public boolean isTaxiCommunicationDeviceFitted() {
		return taxiCommunicationDeviceFitted;
	}
	public void setTaxiCommunicationDeviceFitted(boolean taxiCommunicationDeviceFitted) {
		this.taxiCommunicationDeviceFitted = taxiCommunicationDeviceFitted;
	}
	public Integer getTaxiCommunicationDeviceValue() {
		return taxiCommunicationDeviceValue;
	}
	public void setTaxiCommunicationDeviceValue(Integer taxiCommunicationDeviceValue) {
		this.taxiCommunicationDeviceValue = taxiCommunicationDeviceValue;
	}
	public String getTaxiDateProprietorCommenced() {
		return taxiDateProprietorCommenced;
	}
	public void setTaxiDateProprietorCommenced(String taxiDateProprietorCommenced) {
		this.taxiDateProprietorCommenced = taxiDateProprietorCommenced;
	}
	public Integer getTaxiFareCarryingCapacity() {
		return taxiFareCarryingCapacity;
	}
	public void setTaxiFareCarryingCapacity(Integer taxiFareCarryingCapacity) {
		this.taxiFareCarryingCapacity = taxiFareCarryingCapacity;
	}
	public String getTaxiLocalAuthorityPostcode() {
		return taxiLocalAuthorityPostcode;
	}
	public void setTaxiLocalAuthorityPostcode(String taxiLocalAuthorityPostcode) {
		this.taxiLocalAuthorityPostcode = taxiLocalAuthorityPostcode;
	}
	public String getTaxiLocalLicencingAuthority() {
		return taxiLocalLicencingAuthority;
	}
	public void setTaxiLocalLicencingAuthority(String taxiLocalLicencingAuthority) {
		this.taxiLocalLicencingAuthority = taxiLocalLicencingAuthority;
	}
	public String getTaxiLocalLicencingAuthorityDescription() {
		return taxiLocalLicencingAuthorityDescription;
	}
	public void setTaxiLocalLicencingAuthorityDescription(String taxiLocalLicencingAuthorityDescription) {
		this.taxiLocalLicencingAuthorityDescription = taxiLocalLicencingAuthorityDescription;
	}
	public Integer getTaxiModificationValue() {
		return taxiModificationValue;
	}
	public void setTaxiModificationValue(Integer taxiModificationValue) {
		this.taxiModificationValue = taxiModificationValue;
	}
	public String getTaxiNcbTransferredFrom() {
		return taxiNcbTransferredFrom;
	}
	public void setTaxiNcbTransferredFrom(String taxiNcbTransferredFrom) {
		this.taxiNcbTransferredFrom = taxiNcbTransferredFrom;
	}
	public String getTaxiNcbTransferredFromDescription() {
		return taxiNcbTransferredFromDescription;
	}
	public void setTaxiNcbTransferredFromDescription(String taxiNcbTransferredFromDescription) {
		this.taxiNcbTransferredFromDescription = taxiNcbTransferredFromDescription;
	}
	public String getTransmission() {
		return transmission;
	}
	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}
	public String getTransmissionDescription() {
		return transmissionDescription;
	}
	public void setTransmissionDescription(String transmissionDescription) {
		this.transmissionDescription = transmissionDescription;
	}
	public Integer getAnnualBusinessMileage() {
		return annualBusinessMileage;
	}
	public void setAnnualBusinessMileage(Integer annualBusinessMileage) {
		this.annualBusinessMileage = annualBusinessMileage;
	}
	public Integer getAnnualPleasureMileage() {
		return annualPleasureMileage;
	}
	public void setAnnualPleasureMileage(Integer annualPleasureMileage) {
		this.annualPleasureMileage = annualPleasureMileage;
	}
	public String getBodyType() {
		return bodyType;
	}
	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}
	public String getBodyTypeDescription() {
		return bodyTypeDescription;
	}
	public void setBodyTypeDescription(String bodyTypeDescription) {
		this.bodyTypeDescription = bodyTypeDescription;
	}
	public String getClassOfUse() {
		return classOfUse;
	}
	public void setClassOfUse(String classOfUse) {
		this.classOfUse = classOfUse;
	}
	public String getClassOfUseDescription() {
		return classOfUseDescription;
	}
	public void setClassOfUseDescription(String classOfUseDescription) {
		this.classOfUseDescription = classOfUseDescription;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getColourDescription() {
		return colourDescription;
	}
	public void setColourDescription(String colourDescription) {
		this.colourDescription = colourDescription;
	}
	public Integer getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}
	public String getDatePurchased() {
		return datePurchased;
	}
	public void setDatePurchased(String datePurchased) {
		this.datePurchased = datePurchased;
	}
	public Integer getEngineCC() {
		return engineCC;
	}
	public void setEngineCC(Integer engineCC) {
		this.engineCC = engineCC;
	}
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	public String getFinishDescription() {
		return finishDescription;
	}
	public void setFinishDescription(String finishDescription) {
		this.finishDescription = finishDescription;
	}
	public String getFuel() {
		return fuel;
	}
	public void setFuel(String fuel) {
		this.fuel = fuel;
	}
	public String getFuelDescription() {
		return fuelDescription;
	}
	public void setFuelDescription(String fuelDescription) {
		this.fuelDescription = fuelDescription;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLeftRightHandDrive() {
		return leftRightHandDrive;
	}
	public void setLeftRightHandDrive(String leftRightHandDrive) {
		this.leftRightHandDrive = leftRightHandDrive;
	}
	public String getLeftRightHandDriveDescription() {
		return leftRightHandDriveDescription;
	}
	public void setLeftRightHandDriveDescription(String leftRightHandDriveDescription) {
		this.leftRightHandDriveDescription = leftRightHandDriveDescription;
	}
	public String getManufacturerAbiCode() {
		return manufacturerAbiCode;
	}
	public void setManufacturerAbiCode(String manufacturerAbiCode) {
		this.manufacturerAbiCode = manufacturerAbiCode;
	}
	public String getManufacturerDescription() {
		return manufacturerDescription;
	}
	public void setManufacturerDescription(String manufacturerDescription) {
		this.manufacturerDescription = manufacturerDescription;
	}
	public String getModelAbiCode() {
		return modelAbiCode;
	}
	public void setModelAbiCode(String modelAbiCode) {
		this.modelAbiCode = modelAbiCode;
	}
	public String getModelDescription() {
		return modelDescription;
	}
	public void setModelDescription(String modelDescription) {
		this.modelDescription = modelDescription;
	}
	public List<Modification> getModificationList() {
		return modificationList;
	}
	public void setModificationList(List<Modification> modificationList) {
		this.modificationList = modificationList;
	}
}
