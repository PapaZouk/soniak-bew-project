import axios from "axios";
import { Login, Register } from "../model/auth";

async function login(login: Login): Promise<any> {
  try {
    const response = await axios.post("http://localhost:8080/login", login);

    return response.data;
  } catch (e) {
    throw new Error("Could not login");
  }
}

async function register(register: Register): Promise<number> {
  try {
    const response = await axios.post(
      "http://localhost:8080/register/",
      register
    );
    return response.data;
  } catch (error) {
    throw new Error("Registration failed! Please try again.");
  }
}

export { login, register };
