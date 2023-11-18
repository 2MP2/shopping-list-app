import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import SaveIcon from '@mui/icons-material/Save';
import CancelIcon from '@mui/icons-material/Close';
import {
    GridRowsProp,
    GridRowModesModel,
    GridRowModes,
    DataGrid,
    GridColDef,
    GridToolbarContainer,
    GridActionsCellItem,
    GridEventListener,
    GridRowId,
    GridRowModel,
    GridRowEditStopReasons,
} from '@mui/x-data-grid';
import {useLocation, useNavigate, useParams} from "react-router-dom";
import {addProduct, deleteProduct, getProductList, updateProduct} from "../../service/product";
import {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {v4 as uuidv4} from 'uuid';
import {ProductRequestDTO} from "../../model/dto/request";
import {ProductResponseDTO} from "../../model/dto/response";
import {Description, RequestQuote} from "@mui/icons-material";

interface EditToolbarProps {
    setRows: (newRows: (oldRows: GridRowsProp) => GridRowsProp) => void;
    setRowModesModel: (
        newModel: (oldModel: GridRowModesModel) => GridRowModesModel,
    ) => void;
}

function EditToolbar(props: EditToolbarProps) {
    const { id } = useParams();
    const { setRows, setRowModesModel } = props;
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const shoppingListId = queryParams.get('shopping-list');
    const navigate = useNavigate();

    const handleClick = () => {
        const id = uuidv4();
        setRows((oldRows) => [...oldRows, { id, name: '', quantity: 0, purchased: false, isNew: true }]);
        setRowModesModel((oldModel) => ({
            ...oldModel,
            [id]: { mode: GridRowModes.Edit, fieldToFocus: 'name' },
        }));
    };

    const navigateToBill = () => {
        if(shoppingListId && id){
            navigate(`/organization/bill-create/${id}/?shopping-list=${shoppingListId}`)
        }
    }

    const navigateToSettlements = () => {
        if(shoppingListId && id){
            navigate(`/organization/settlements/${id}/?shopping-list=${shoppingListId}`)
        }
    }

    return (
        <GridToolbarContainer>
            <Button color="primary" startIcon={<AddIcon />} onClick={handleClick}>
                Add product
            </Button>
            <Button color="primary" startIcon={<Description />} onClick={navigateToBill}>
                Create Bill
            </Button>
            <Button color="primary" startIcon={<RequestQuote />} onClick={navigateToSettlements}>
                Show Settlements
            </Button>
        </GridToolbarContainer>
    );
}

export default function ProductCrudGrid() {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const shoppingListId = queryParams.get('shopping-list');
    const [rows, setRows] = useState<GridRowsProp>([]);
    const [rowModesModel, setRowModesModel] = useState<GridRowModesModel>({});


    useEffect(() => {
        if(shoppingListId){
            getProductList({shoppingList: shoppingListId, bill: '' })
                .then((response) => {
                    setRows(response.content);
                })
                .catch((error) => {
                    toast.error("Couldn't load products");
                });
        }
    }, [shoppingListId]);


    const handleRowEditStop: GridEventListener<'rowEditStop'> = (params: any, event: any) => {
        if (params.reason === GridRowEditStopReasons.rowFocusOut) {
            event.defaultMuiPrevented = true;
        }
    };

    const handleEditClick = (id: GridRowId) => () => {
        setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.Edit } });
    };

    const handleSaveClick = (id: GridRowId) => () => {
        setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.View } });
    };

    const handleDeleteClick = (id: GridRowId) => async () => {
        await deleteProduct(id.toString());
        setRows(rows.filter((row: GridRowModel ) => row.id !== id));
    };

    const handleCancelClick = (id: GridRowId) => () => {
        setRowModesModel({
            ...rowModesModel,
            [id]: { mode: GridRowModes.View, ignoreModifications: true },
        });

        const editedRow = rows.find((row: GridRowModel ) => row.id === id);
        if (editedRow!.isNew) {
            setRows(rows.filter((row: GridRowModel ) => row.id !== id));
        }
    };

    const processRowUpdate = async (newRow: GridRowModel) => {
        if(shoppingListId){
            const product = {...newRow};
            const productRequestDTO: ProductRequestDTO = {
                name: product.name,
                quantity: product.quantity,
                purchased: product.purchased,
                shoppingListId: shoppingListId,
            }

            let productResponseDTO: ProductResponseDTO;
            if(newRow.isNew){
                productResponseDTO = await addProduct(productRequestDTO);
            }else{
                productResponseDTO = await updateProduct(newRow.id, productRequestDTO);
            }

            const updatedRow = { ...newRow,id: productResponseDTO.id, isNew: false };
            setRows(rows.map((row: GridRowModel) => (row.id === newRow.id ? updatedRow : row)));
            return updatedRow;
        }
    };

    const handleRowModesModelChange = (newRowModesModel: GridRowModesModel) => {
        setRowModesModel(newRowModesModel);
    };

    const columns: GridColDef[] = [
        {
            field: 'purchased',
            headerName: 'Purchased',
            width: 120,
            editable: true,
            type: 'boolean'
        },
        { field: 'name', headerName: 'Name', width: 240, editable: true },
        {
            field: 'quantity',
            headerName: 'Quantity',
            type: 'number',
            width: 80,
            editable: true,
            align: 'center',
            headerAlign: 'center',
        },
        {
            field: 'actions',
            type: 'actions',
            headerName: 'Actions',
            width: 100,
            cellClassName: 'actions',
            getActions: ({ id }: { id: GridRowId }) => {
                const isInEditMode = rowModesModel[id]?.mode === GridRowModes.Edit;

                if (isInEditMode) {
                    return [
                        <GridActionsCellItem
                            icon={<SaveIcon />}
                            label="Save"
                            sx={{
                                color: 'primary.main',
                            }}
                            onClick={handleSaveClick(id)}
                        />,
                        <GridActionsCellItem
                            icon={<CancelIcon />}
                            label="Cancel"
                            className="textPrimary"
                            onClick={handleCancelClick(id)}
                            color="inherit"
                        />,
                    ];
                }

                return [
                    <GridActionsCellItem
                        icon={<EditIcon />}
                        label="Edit"
                        className="textPrimary"
                        onClick={handleEditClick(id)}
                        color="inherit"
                    />,
                    <GridActionsCellItem
                        icon={<DeleteIcon />}
                        label="Delete"
                        onClick={handleDeleteClick(id)}
                        color="inherit"
                    />,
                ];
            },
        },
    ];

    return (
        <Box
            sx={{
                height: '80vh',
                width: '35%',
                minWidth: 600,
                marginRight: '20px',
                marginTop: '20px',
                '& .actions': {
                    color: 'text.secondary',
                },
                '& .textPrimary': {
                    color: 'text.primary',
                },
            }}
        >
            <DataGrid
                rows={rows}
                columns={columns}
                editMode="row"
                rowModesModel={rowModesModel}
                onRowModesModelChange={handleRowModesModelChange}
                onRowEditStop={handleRowEditStop}
                processRowUpdate={processRowUpdate}
                slots={{
                    toolbar: EditToolbar,
                }}
                slotProps={{
                    toolbar: { setRows, setRowModesModel },
                }}
            />
        </Box>
    );
}