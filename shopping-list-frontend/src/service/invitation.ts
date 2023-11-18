import { axiosInstance } from "./axios";
import { InvitationResponseDTO, Page } from "../model/dto/response";
import { InvitationRequestDTO } from "../model/dto/request";

export async function getInvitationList(
  params: Record<string, string>,
): Promise<Page<InvitationResponseDTO>> {
  const response = await axiosInstance.get<Page<InvitationResponseDTO>>(
    "/invitation",
    {
      params,
    },
  );
  return response.data;
}

export async function getInvitationById(
  id: string,
): Promise<InvitationResponseDTO> {
  const response = await axiosInstance.get<InvitationResponseDTO>(
    `/invitation/${id}`,
  );
  return response.data;
}

export async function addInvitation(
  invitationData: InvitationRequestDTO,
): Promise<InvitationResponseDTO> {
  const response = await axiosInstance.post<InvitationResponseDTO>(
    "/invitation",
    invitationData,
  );
  return response.data;
}

export async function updateInvitation(
  id: string,
  invitationData: InvitationRequestDTO,
): Promise<InvitationResponseDTO> {
  const response = await axiosInstance.put<InvitationResponseDTO>(
    `/invitation/${id}`,
    invitationData,
  );
  return response.data;
}

export async function deleteInvitationById(id: string): Promise<void> {
  await axiosInstance.delete(`/invitation/${id}`);
}

export async function acceptInvitation(
  id: string,
  isAccepted: boolean,
): Promise<void> {
  await axiosInstance.post(`/invitation/accept/${id}?isAccepted=${isAccepted}`);
}

export async function countUserInvitation(
    userId: string,
): Promise<Number> {
  const response = await axiosInstance.get<Number>(
      `/invitation/count/${userId}`,
  );
  return response.data;
}
