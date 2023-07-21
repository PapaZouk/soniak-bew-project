import { Application, Request, Response } from "express";
import { Client, MaxValue, ClientToProject } from "../model/client";
import * as clientService from "../service/clientService";

module.exports = function (app: Application) {
  app.get("/clients", async (req: Request, res: Response) => {
    let data: Client[];

    try {
      data = await clientService.getClients(req.session.token);
    } catch (error) {
      console.log("Controller", error);
    }
    res.render("sales/list-clients", { clients: data });
  });

  app.get("/maxvalue", async (req: Request, res: Response) => {
    let data: MaxValue;

    try {
      data = await clientService.getMaxValue(req.session.token);
    } catch (error) {
      console.log("Controller Error", error);
    }
    res.render("sales/view-max-value", { maxValue: data });
  });

  app.get("/add-project-id", async (req: Request, res: Response) => {
    if (!req.session.clientToProject) {
      req.session.clientToProject = {};
    }

    res.render("sales/add-project-id");
  });

  app.post("/add-project-id", async (req: Request, res: Response) => {
    req.session.clientToProject["projectId"] = req.body.projectId;
    res.redirect("/add-client-id");
  });

  app.get("/add-client-id", async (req: Request, res: Response) => {
    res.render("sales/add-client-id");
  });

  app.post("/add-client-id", async (req: Request, res: Response) => {
    req.session.clientToProject["clientId"] = req.body.clientId;
    res.redirect("add-clientToProject-confirmation");
  });

  app.get(
    "/add-clientToProject-confirmation",
    async (req: Request, res: Response) => {
      res.render(
        "sales/add-clientToProject-confirmation",
        req.session.clientToProject
      );
    }
  );

  app.post(
    "/add-clientToProject-confirmation",
    async (req: Request, res: Response) => {
      let data: ClientToProject = req.session.clientToProject;

      try {
        await clientService.addClientToProject(data, req.session.token);
        req.session.clientToProject = undefined;
        res.redirect("/");
      } catch (error) {
        console.log(error);
        res.locals.errormessage = error.message;
        res.render(
          "sales/add-clientToProject-confirmation",
          req.session.clientToProject
        );
      }
    }
  );
};
