import axios from "axios";
import { Client, MaxValue } from "../model/client";
// const orderValidator = require("../validator/orderValidator");

async function getClients(): Promise<Client[]> {
  try {
    const response = await axios.get("http://localhost:8080/clients");
    return response.data;
  } catch (error) {
    throw new Error("Could not get clients");
  }
}

async function getMaxValue(): Promise<MaxValue> {
  try {
    const response = await axios.get("http://localhost:8080/clients/maxvalue");

    return response.data;
  } catch (error) {
    throw new Error("Could not get max value");
  }
}

export { getClients, getMaxValue };
