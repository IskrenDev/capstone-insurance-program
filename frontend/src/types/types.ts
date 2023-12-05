export type Insurance = {
    id?:string,
    firstName: string,
    familyName:string,
    zipCode:string,
    city:string,
    address:string,
    telephone:string,
    email:string,
    type: "LIFE" | "PROPERTY" | "VEHICLE",
    duration:number,
    paymentPerMonth:number,
    startDate:string,
    endDate:string,
    hasHealthIssues:boolean,
    healthConditionDetails: string
    propertyType: string,
    propertyAddress: string,
    constructionYear: number,
    vehicleMake: string,
    vehicleModel: string;
    vehicleYear: number;
    licensePlateNumber: string
};

export type AllInsurancesResponse = {
    lifeInsurances: Insurance[];
    propertyInsurances: Insurance[];
    vehicleInsurances: Insurance[];
};