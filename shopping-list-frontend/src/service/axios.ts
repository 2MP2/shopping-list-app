import axios from "axios";

export const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVICE_URI,
  timeout: 10000,
});

export const updateAuthToken = (authToken: string) => {
  console.log(authToken);
  axiosInstance.defaults.headers.common[
    "Authorization"
  ] = `Bearer ${authToken}`;
};