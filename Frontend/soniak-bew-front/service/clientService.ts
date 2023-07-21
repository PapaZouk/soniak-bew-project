import axios from "axios";
import { Client, ClientToProject, MaxValue } from "../model/client";
// const orderValidator = require("../validator/orderValidator");

async function getClients(token: string): Promise<Client[]> {
  try {
    const response = await axios.get("http://localhost:8080/clients", {
      params: { token: token },
    });
    return response.data;
  } catch (error) {
    throw new Error("Could not get clients");
  }
}

async function getMaxValue(token: string): Promise<MaxValue> {
  try {
    const response = await axios.get("http://localhost:8080/clients/maxvalue", {
      params: { token: token },
    });

    return response.data;
  } catch (error) {
    throw new Error("Could not get max value");
  }
}

async function addClientToProject(
  clientToProject: ClientToProject,
  token: string
): Promise<number> {
  try {
    const { projectId, clientId } = clientToProject;
    const response = await axios.post(
      `http://localhost:8080/projects/${projectId}/client/${clientId}`,
      {
        params: {
          id: projectId,
          clientId,
          token,
        },
      }
    );

    return response.data;
  } catch (error) {
    throw new Error("Could not add client to project");
  }
}

export { getClients, getMaxValue, addClientToProject };
