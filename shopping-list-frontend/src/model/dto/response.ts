import {ProductStatus} from "../status";

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
    shoppingLists: ShoppingListLightResponseDTO[];
};

export type ProductResponseDTO = {
    id: string;
    name: string;
    quantity: number;
    status: ProductStatus;
};

export type ShoppingListLightResponseDTO = {
    id: string;
    name: string;
};

export type ShoppingListResponseDTO = {
    id: string;
    name: string;
    products: ProductResponseDTO[];
};

export type UserOrganizationResponseDTO = {
    id: string;
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