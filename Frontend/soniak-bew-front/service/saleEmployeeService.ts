import { SaleEmployee } from "../model/saleEmployee";
const axios = require('axios');

module.exports.getSaleEmployees = async function (): Promise <SaleEmployee[]> {
    try {
        const response = await axios.get('http://localhost:8080/employees/salesman')

        return response.data
    } catch (e) {
        throw new Error('Could not get Delivery Salesman')
    }
}