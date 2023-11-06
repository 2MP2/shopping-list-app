import { Page, ShoppingListResponseDTO } from "../model/dto/response";
import { axiosInstance } from "./axios";
import { ShoppingListRequestDTO } from "../model/dto/request";

export async function getShoppingList(
  params: any,
): Promise<Page<ShoppingListResponseDTO>> {
  const response = await axiosInstance.get<Page<ShoppingListResponseDTO>>(
    "/shopping-list",
    {
      params,
    },
  );
  return response.data;
}

export async function getShoppingListById(
  id: string,
): Promise<ShoppingListResponseDTO> {
  const response = await axiosInstance.get<ShoppingListResponseDTO>(
    `/shopping-list/${id}`,
  );
  return response.data;
}

export async function addShoppingList(
  shoppingListRequestDTO: ShoppingListRequestDTO,
): Promise<ShoppingListResponseDTO> {
  const response = await axiosInstance.post<ShoppingListResponseDTO>(
    "/shopping-list",
    shoppingListRequestDTO,
  );
  return response.data;
}

export async function updateShoppingList(
  id: string,
  shoppingListRequestDTO: ShoppingListRequestDTO,
): Promise<ShoppingListResponseDTO> {
  const response = await axiosInstance.put<ShoppingListResponseDTO>(
    `/shopping-list/${id}`,
    shoppingListRequestDTO,
  );
  return response.data;
}

export async function deleteShoppingList(id: string): Promise<void> {
  await axiosInstance.delete(`/shopping-list/${id}`);
}