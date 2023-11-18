import { Page, UserResponseDTO } from "../model/dto/response";
import { axiosInstance } from "./axios";
import { UserRequestDTO } from "../model/dto/request";

export async function getUserList(
  params: Record<string, string>,
): Promise<Page<UserResponseDTO>> {
  const response = await axiosInstance.get<Page<UserResponseDTO>>("/user", {
    params,
  });
  return response.data;
}

export async function getUserById(id: string): Promise<UserResponseDTO> {
  const response = await axiosInstance.get<UserResponseDTO>(`/user/${id}`);
  return response.data;
}

export async function addUser(
  userRequestDTO: UserRequestDTO,
): Promise<UserResponseDTO> {
  const response = await axiosInstance.post<UserResponseDTO>(
    "/user",
    userRequestDTO,
  );
  return response.data;
}

export async function updateUser(
  id: string,
  userRequestDTO: UserRequestDTO,
): Promise<UserResponseDTO> {
  const response = await axiosInstance.put<UserResponseDTO>(
    `/user/${id}`,
    userRequestDTO,
  );
  return response.data;
}

export async function deleteUserById(id: string): Promise<void> {
  await axiosInstance.delete(`/user/${id}`);
}

export async function getFullUserInfo(id: string): Promise<UserResponseDTO> {
  const response = await axiosInstance.get<UserResponseDTO>(`/user/info/${id}`);
  return response.data;
}