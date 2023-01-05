export interface Transaction {
    transaction_id: number;
    transaction_type_id: number;
    product_id: number;
    transaction_date: Date;
    description: string;
    transaction_value: number;
    destination_product_id?: number;
    origin_product_id?: number;
}