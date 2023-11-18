import { AuthenticationRequest, UserRequestDTO } from "../model/dto/request";
import { AuthenticationResponse } from "../model/dto/response";
import {axiosInstance, removeAuthToken, updateAuthToken} from "./axios";
import { jwtDecode } from "jwt-decode";

export async function login(
  userData: AuthenticationRequest,
): Promise<AuthenticationResponse> {
  const response = await axiosInstance.post<AuthenticationResponse>(
    "/authentication/login",
    userData,
  );
  const authToken = response.data.token;
  const decodedToken: any = jwtDecode(authToken);
  await updateAuthToken(authToken);
  sessionStorage.setItem('id', decodedToken.id);

  return response.data;
}

export async function register(
  userData: UserRequestDTO,
): Promise<AuthenticationResponse> {
  const response = await axiosInstance.post<AuthenticationResponse>(
    "/authentication/register",
    userData,
  );
  const authToken = response.data.token;
  const decodedToken: any = jwtDecode(authToken);
  await updateAuthToken(authToken);
  sessionStorage.setItem('id', decodedToken.id);

  return response.data;
}

export function logout(){
  removeAuthToken();
  sessionStorage.removeItem('id');
}