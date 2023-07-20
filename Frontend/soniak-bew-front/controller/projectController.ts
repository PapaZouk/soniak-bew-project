import { Application, Request, Response } from "express";
import { Project } from "../model/project";
import * as projectService from "../service/projectService";

module.exports = function (app: Application) {
  app.get("/projects", async (req: Request, res: Response) => {
    let data: Project[];

    try {
      data = await projectService.getProjects(req.session.token);
    } catch (error) {
      console.log("Controller", error);
    }
    res.render("management/list-projects", { projects: data });
  });

  //   app.get("/maxvalue", async (req: Request, res: Response) => {
  //     let data: MaxValue;

  //     try {
  //       data = await clientService.getMaxValue(req.session.token);
  //     } catch (error) {
  //       console.log("Controller Error", error);
  //     }
  //     res.render("sales/view-max-value", { maxValue: data });
  //   });
};
