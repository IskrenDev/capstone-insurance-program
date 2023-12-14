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
    hasHealthIssues?:boolean,
    healthConditionDetails?: string
    propertyType?: string,
    propertyAddress?: string,
    constructionYear?: number,
    vehicleMake?: string,
    vehicleModel?: string;
    vehicleYear?: number;
    licensePlateNumber?: string
};

export type FormLabelProps = {
    label: string;
    name: string;
    value?: string | number | readonly string[]
    handleOnChangeNumber?: (v: number) => void;
    handleOnChangeText?: (v: string) => void;
    handleOnChangeCheckbox?: (v: boolean) => void;
    handleOnChangeDate?: (v: string) => void;
    isRequired?: boolean;
    checked?: boolean
    options?: { value: string; label: string }[];
    textarea?: boolean;
    pattern?: string;
    startDate?: string;
    endDate?: string;
    type?: "date" | "number" | "checkbox" | "text" | "email" | "tel";
    isReadOnly?: boolean;
}

export type DetailsLabelProps = {
    label: string;
    value?: string | number | boolean;
    insuranceType?: { value: string; label: string }[];
    type?: "email" | "tel" | "text";
};

export type InsuranceListProps = {
    insurances: Insurance[];
    headerText: string;
    type: string;
};

export type AllInsurancesResponse = {
    lifeInsurances: Insurance[];
    propertyInsurances: Insurance[];
    vehicleInsurances: Insurance[];
};

export type AppUser = {
    id: string,
    login: string,
};

export type ProtectedRoutesProps = {
    appUser: AppUser | null | undefined;
};