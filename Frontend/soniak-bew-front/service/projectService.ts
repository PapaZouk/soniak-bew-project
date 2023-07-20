import axios from "axios";
import { Project } from "../model/project";
// const orderValidator = require("../validator/orderValidator");

async function getProjects(token: string): Promise<Project[]> {
  try {
    const response = await axios.get("http://localhost:8080/projects", {
      params: { token: token },
    });
    return response.data;
  } catch (error) {
    throw new Error("Could not get projects");
  }
}

export { getProjects };
