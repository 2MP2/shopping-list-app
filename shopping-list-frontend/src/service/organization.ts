import { OrganizationResponseDTO, Page } from "../model/dto/response";
import { axiosInstance } from "./axios";
import { OrganizationRequestDTO } from "../model/dto/request";

export async function getOrganizationList(
  params: any,
): Promise<Page<OrganizationResponseDTO>> {
  const response = await axiosInstance.get<Page<OrganizationResponseDTO>>(
    "/organization",
    {
      params,
    },
  );
  return response.data;
}

export async function getOrganizationById(
  id: string,
): Promise<OrganizationResponseDTO> {
  const response = await axiosInstance.get<OrganizationResponseDTO>(
    `/organization/${id}`,
  );
  return response.data;
}

export async function addOrganization(
  organizationRequestDTO: OrganizationRequestDTO,
): Promise<OrganizationResponseDTO> {
  const response = await axiosInstance.post<OrganizationResponseDTO>(
    "/organization",
    organizationRequestDTO,
  );
  return response.data;
}

export async function updateOrganization(
  id: string,
  organizationRequestDTO: OrganizationRequestDTO,
): Promise<OrganizationResponseDTO> {
  const response = await axiosInstance.put<OrganizationResponseDTO>(
    `/organization/${id}`,
    organizationRequestDTO,
  );
  return response.data;
}

export async function deleteOrganization(id: string): Promise<void> {
  await axiosInstance.delete(`/organization/${id}`);
}
