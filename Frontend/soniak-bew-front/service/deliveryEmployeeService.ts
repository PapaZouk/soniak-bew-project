import { DeliveryEmployee } from "../model/deliveryEmployee";

const axios = require('axios');

module.exports.getDeliveryEmployees = async function (): Promise <DeliveryEmployee[]> {
    try {
        const response = await axios.get('http://localhost:8080/employees/deliveryman')

        return response.data
    } catch (e) {
        throw new Error('Could not get Delivery Employees')
    }
}

module.exports.getDeliveryEmployeeById = async function (id: number): Promise<DeliveryEmployee> {
    try {
        const response = await axios.get('http://localhost:8080/employees/deliveryman/' + id)

        return response.data
    } catch (e) {
        throw new Error('Could not get Delivery Employee')
    }
}

module.exports.createDeliveryEmployee = async function (deliveryEmployee: DeliveryEmployee): Promise<number> {
    try {
        const response = await axios.post('http://localhost:8080/employees/deliveryman/create/', deliveryEmployee)

        return response.data
    } catch (e) {
        throw new Error ('Could not create Delivery Employee')
    }
}

module.exports.deleteDeliveryEmployee = async function (id: number): Promise<DeliveryEmployee> {
    try {
        const response = await axios.delete('http://localhost:8080/employees/deliveryman/delete/' + id)

        return response.data
    } catch (e) {
        throw new Error('Could not delete Delivery Employee')
    }
}

module.exports.updateDeliveryEmployee = async function (id: number, deliveryEmployee: DeliveryEmployee): Promise<DeliveryEmployee> {
    console.log(id, deliveryEmployee);
    try {
        const response = await axios.patch('http://localhost:8080/employees/deliveryman/' +  id  + '/update', deliveryEmployee)

        return response.data
    } catch (e) {
        throw new Error(`Could not update Delivery Employee${e}`);
    }
}