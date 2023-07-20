import { Application, Request, Response } from "express";
import { Client, MaxValue } from "../model/client";
import * as clientService from "../service/clientService";

module.exports = function (app: Application) {
  app.get("/clients", async (req: Request, res: Response) => {
    let data: Client[];

    try {
      data = await clientService.getClients();
    } catch (error) {
      console.log("Controller", error);
    }
    res.render("sales/list-clients", { clients: data });
  });

  app.get("/maxvalue", async (req: Request, res: Response) => {
    let data: MaxValue;

    try {
      data = await clientService.getMaxValue();
    } catch (error) {
      console.log("Controller Error", error);
    }
    res.render("sales/view-max-value", { maxValue: data });
  });
};
