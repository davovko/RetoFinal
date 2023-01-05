 export interface Product{
    product_id: number;
    customer_id: number;
    product_type_id: number;
    account_number: string;
    status_account_id: number;
    balance: number;
    available_balance: number;
    gmf_exempt: boolean;
    creation_date: Date;
    modification_date: Date
}
    