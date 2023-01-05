export interface Customer {
    customer_id: number;
    identification_type_Id: number;
    identification_number: string;
    first_name: string;
    middle_name?: string;
    last_name: string;
    second_last_name?: string;
    email: string;
    date_of_birth: Date;
    creation_date?: Date;
    creation_user_id?: number;
    modification_date?: Date;
    modification_user_id?: number;
    status: boolean;
}