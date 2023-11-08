import { UserOrganizationStatus } from "../status";

export type AuthenticationRequest = {
  email: string;
  password: string;
};

export type BillRequestDTO = {
  amount: number;
  shop: string;
  comment: string;
  shoppingListId: string;
  userId: string;
};

export type InvitationRequestDTO = {
  organizationId: string;
  userEmail: string;
  userNumber: string;
};

export type OrganizationRequestDTO = {
  name: string;
};

export type ProductRequestDTO = {
  name: string;
  quantity: number;
  isBought: boolean;
  shoppingListId: string;
};

export type ShoppingListRequestDTO = {
  name: string;
  organizationId: string;
};

export type UserOrganizationRequestDTO = {
  status: UserOrganizationStatus;
  userId: string;
  organizationId: string;
};

export type UserRequestDTO = {
  name: string;
  surname: string;
  number: string;
  email: string;
  password: string;
};