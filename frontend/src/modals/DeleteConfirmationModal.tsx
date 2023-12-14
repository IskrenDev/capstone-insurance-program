import React from 'react';
import '../modals/DeleteConfirmationModal.css';
import WarningIcon from '../components/svg/WarningIcon.tsx';

interface DeleteConfirmationModalProps {
    show: boolean;
    handleClose: () => void;
    handleConfirm: () => void;
}

const DeleteConfirmationModal = (props: DeleteConfirmationModalProps) => {
    const {show, handleClose, handleConfirm} = props;

    const handleCloseKeyDown = (event: React.KeyboardEvent<HTMLButtonElement>) => {
        if (event.key === 'Enter' || event.key === ' ') {
            handleClose();
        }
    };

    const handleConfirmKeyDown = (event: React.KeyboardEvent<HTMLButtonElement>) => {
        if (event.key === 'Enter' || event.key === ' ') {
            handleConfirm();
        }
    };

    return (
        show && (
            <div className="modal-container">
                <div className="modal-content">
                    <div className="modal-header">
                        <WarningIcon/>
                        <div className="modal-title">Bestätigung</div>
                    </div>
                    <div className="modal-body">
                        <p>Sind Sie sicher, dass Sie diesen Versicherungseintrag löschen möchten?</p>
                    </div>
                    <div className="modal-footer">
                        <button
                            className="button-cancel"
                            onClick={handleClose}
                            onKeyDown={handleCloseKeyDown}
                        >
                            Abbrechen
                        </button>
                        <button
                            className="button-confirm"
                            onClick={handleConfirm}
                            onKeyDown={handleConfirmKeyDown}
                        >
                            Löschen
                        </button>
                    </div>
                </div>
            </div>
        )
    );
};

export default DeleteConfirmationModal;