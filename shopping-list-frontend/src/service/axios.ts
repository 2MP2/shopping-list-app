import axios, {AxiosInstance} from "axios";

export const axiosInstance : AxiosInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVICE_URI,
  timeout: 10000,
});

export async function updateAuthToken(authToken: string) : Promise<void> {
  axiosInstance.defaults.headers.common[
    "Authorization"
  ] = `Bearer ${authToken}`;
}

export function removeAuthToken(): void {
  delete axiosInstance.defaults.headers.common["Authorization"];
}