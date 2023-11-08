import { Page, ProductResponseDTO } from "../model/dto/response";
import { axiosInstance } from "./axios";
import { ProductRequestDTO } from "../model/dto/request";

export async function getProductList(
  params: Record<string, string>,
): Promise<Page<ProductResponseDTO>> {
  const response = await axiosInstance.get<Page<ProductResponseDTO>>(
    "/product",
    {
      params,
    },
  );
  return response.data;
}

export async function getProductById(id: string): Promise<ProductResponseDTO> {
  const response = await axiosInstance.get<ProductResponseDTO>(
    `/product/${id}`,
  );
  return response.data;
}

export async function addProduct(
  productRequestDTO: ProductRequestDTO,
): Promise<ProductResponseDTO> {
  const response = await axiosInstance.post<ProductResponseDTO>(
    "/product",
    productRequestDTO,
  );
  return response.data;
}

export async function updateProduct(
  id: string,
  productRequestDTO: ProductRequestDTO,
): Promise<ProductResponseDTO> {
  const response = await axiosInstance.put<ProductResponseDTO>(
    `/product/${id}`,
    productRequestDTO,
  );
  return response.data;
}

export async function deleteProduct(id: string): Promise<void> {
  await axiosInstance.delete(`/product/${id}`);
}