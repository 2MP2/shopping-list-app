import {axiosInstance} from "./axios";
import {Page, UserOrganizationResponseDTO} from "../model/dto/response";
import {UserOrganizationRequestDTO} from "../model/dto/request";

export async function getUserOrganizationList(params: Record<string, string>): Promise<Page<UserOrganizationResponseDTO>> {
    const response = await axiosInstance.get<Page<UserOrganizationResponseDTO>>("/user-organization", {
        params,
    });
    return response.data;
}

export async function addUserToOrganization(userOrganizationRequestDTO: UserOrganizationRequestDTO): Promise<UserOrganizationResponseDTO> {
    const response = await axiosInstance.post<UserOrganizationResponseDTO>("/user-organization", userOrganizationRequestDTO);
    return response.data;
}

export async function updateUserInOrganization(id: string, userOrganizationRequestDTO: UserOrganizationRequestDTO): Promise<UserOrganizationResponseDTO> {
    const response = await axiosInstance.put<UserOrganizationResponseDTO>(`/user-organization/${id}`, userOrganizationRequestDTO);
    return response.data;
}

export async function deleteUserFromOrganization(id: string): Promise<void> {
    await axiosInstance.delete(`/user-organization/${id}`);
}