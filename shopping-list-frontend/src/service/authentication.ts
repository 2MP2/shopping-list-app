import { AuthenticationRequest, UserRequestDTO } from "../model/dto/request";
import { AuthenticationResponse } from "../model/dto/response";
import { axiosInstance, updateAuthToken } from "./axios";

export async function login(
  userData: AuthenticationRequest,
): Promise<AuthenticationResponse> {
  const response = await axiosInstance.post<AuthenticationResponse>(
    "/authentication/login",
    userData,
  );
  const authToken = response.data.token;
  await updateAuthToken(authToken);

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
  await updateAuthToken(authToken);

  return response.data;
}