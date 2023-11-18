import {BillResponseDTO, Page, TransactionResponseDTO} from "../model/dto/response";
import { axiosInstance } from "./axios";
import { BillRequestDTO } from "../model/dto/request";

export async function getBillList(
  params: Record<string, string>,
): Promise<Page<BillResponseDTO>> {
  const response = await axiosInstance.get<Page<BillResponseDTO>>("/bill", {
    params,
  });

  return response.data;
}

export async function getBillById(id: string): Promise<BillResponseDTO> {
  const response = await axiosInstance.get<BillResponseDTO>(`/bill/${id}`);
  return response.data;
}

export async function getTransactionsByShoppingId(id: string): Promise<TransactionResponseDTO[]> {
  const response = await axiosInstance.get<TransactionResponseDTO[]>(`/bill/transaction/${id}`);
  return response.data;
}

export async function addBill(
  billRequestDTO: BillRequestDTO,
): Promise<BillResponseDTO> {
  const response = await axiosInstance.post<BillResponseDTO>(
    "/bill",
    billRequestDTO,
  );
  return response.data;
}

export async function updateBill(
  id: string,
  billRequestDTO: BillRequestDTO,
): Promise<BillResponseDTO> {
  const response = await axiosInstance.put<BillResponseDTO>(
    `/bill/${id}`,
    billRequestDTO,
  );
  return response.data;
}

export async function deleteBillById(id: string): Promise<void> {
  await axiosInstance.delete(`/bill/${id}`);
}
