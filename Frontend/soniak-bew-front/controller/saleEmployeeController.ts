import { Application, Request, Response } from "express";
import { SaleEmployee } from "../model/saleEmployee";

const saleEmployeeService = require("../service/saleEmployeeService")
module.exports = function(app: Application) {
    app.get('/sale-employees', async (req: Request, res: Response) => {
        let data: SaleEmployee[]

        try {
            data = await saleEmployeeService.getSaleEmployees()
        } catch (e) {
            console.error(e);
        }

        res.render('hr/list-sales-employee', {saleEmployees: data})
    })

}
