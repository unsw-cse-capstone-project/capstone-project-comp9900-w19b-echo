export class Property {
  streetName: string
  streetNumber: number;
  suburb: string;
  postcode: string;
  state: string;
  country: string;
  noOfBedroom: number;
  noOfBathroom: number;
  noOfParking: number;
  description: string;
  owner: number;
  propertyType: number;
  status: string;
  bidStartDate: Date;
  bidEndDate: Date;

  address () {
    return this.streetNumber + ' ' + this.streetName + ', ' + this.suburb + ' ' + this.state + ' ' + this.postcode;
  }
}
