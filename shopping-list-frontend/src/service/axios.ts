import axios, {AxiosInstance} from "axios";

export const axiosInstance : AxiosInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVICE_URI,
  timeout: 10000,
});

export async function updateAuthToken(authToken: string) : Promise<void> {
  sessionStorage.setItem("authToken", authToken);
}

export function removeAuthToken(): void {
  sessionStorage.removeItem("authToken");
}

axiosInstance.interceptors.request.use(
    (config) => {
      const authToken = sessionStorage.getItem("authToken");
      if (authToken) {
        config.headers["Authorization"] = `Bearer ${authToken}`;
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
);