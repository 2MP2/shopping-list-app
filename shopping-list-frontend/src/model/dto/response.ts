import {UserOrganizationStatus} from "../status";

export type AuthenticationResponse = {
  token: string;
};

export type BillResponseDTO = {
  id: string;
  amount: number;
  shop: string;
  comment: string;
  products: ProductResponseDTO[];
  user: UserResponseDTO;
};

export type InvitationResponseDTO = {
  id: string;
  organizationId: string;
  organizationName: string;
  userName: string;
  userSurname: string;
};

export type OrganizationResponseDTO = {
  id: string;
  name: string;
};

export type ProductResponseDTO = {
  id: string;
  name: string;
  quantity: number;
  purchased: boolean;
  billId: string;
};

export type ShoppingListResponseDTO = {
  id: string;
  name: string;
};

export type UserOrganizationResponseDTO = {
  id: string;
  status: UserOrganizationStatus;
  user: UserResponseDTO;
  organizationId: string;
  organizationName: string;
};

export type UserResponseDTO = {
  id: string;
  name: string;
  surname: string;
  number: string;
  email: string;
};

export type Page<T> = {
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
  numberOfElements: number;
  size: number;
  content: T[];
  number: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  pageable: {
    offset: number;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    pageSize: number;
    pageNumber: number;
    unpaged: boolean;
    paged: boolean;
  };
  empty: boolean;
};
