import { Application, Request, Response } from "express";
import { DeliveryEmployee } from "../model/deliveryEmployee";

const deliveryEmployeeService = require("../service/deliveryEmployeeService")

module.exports = function(app: Application) {
    app.get('/delivery-employees', async (req: Request, res: Response) => {
        let data: DeliveryEmployee[]

        try {
            data = await deliveryEmployeeService.getDeliveryEmployees()
        } catch (e) {
            console.error(e);
        }

        res.render('hr/list-delivery-employees', {deliveryEmployees: data})
    })

    app.get('/delivery-employees/:id', async (req: Request, res: Response) => {
        let data = []

        try {
            data = await deliveryEmployeeService.getDeliveryEmployeeById(req.params.id);
        } catch (e) {
            console.error(e);
        }

        res.render('hr/view-delivery-employee', {deliveryEmployee: data})
    })

    app.post('/delete-delivery-employee/:id', async (req: Request, res: Response) => {
        let data = []
        try {
            data = await deliveryEmployeeService.deleteDeliveryEmployee(req.params.id);
        } catch (e) {
            console.error(e);
        }
        res.render('hr/list-delivery-employees', {deliveryEmployee: data})
    })

    app.get('/add-delivery-employee', async (req: Request, res: Response) => {
        res.render('hr/add-delivery-employee')
    })

    app.post('/add-delivery-employee', async (req: Request, res: Response) => {
        let data: DeliveryEmployee = req.body
        let id: Number

        try {
            id = await deliveryEmployeeService.createDeliveryEmployee(data)

            res.redirect('/delivery-employees/' + id)
        } catch (e) {
            console.error(e);

            res.locals.errormessage = e.message

            res.render('hr/add-delivery-employee', req.body)
        }
    })
}