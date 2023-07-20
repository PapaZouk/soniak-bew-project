import { Application, Request, Response } from "express";
import { Login, Register } from "../model/auth";
import * as authService from "../service/authService";

module.exports = function (app: Application) {
  app.get("/login", async (req: Request, res: Response) => {
    res.render("login");
  });

  app.post("/login", async (req: Request, res: Response) => {
    let data: Login = req.body;

    try {
      req.session.token = await authService.login(data);
      res.redirect("/");
    } catch (error) {
      console.error(error);
      res.locals.errormessage = error.message;
      res.render("login", req.body);
    }
  });

  app.get("/register", async (req: Request, res: Response) => {
    res.render("register");
  });

  app.post("/register", async (req: Request, res: Response) => {
    let data: Register = req.body;

    try {
      await authService.register(data);
      res.redirect("/login");
    } catch (error) {
      console.error(error);
      res.locals.errormessage = error.message;
      res.render("register", req.body);
    }
  });
};
