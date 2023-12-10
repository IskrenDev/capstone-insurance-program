import React from 'react';
import WarningIcon from '../components/svg/WarningIcon.tsx';

interface DeleteConfirmationModalProps {
    show: boolean;
    handleClose: () => void;
    handleConfirm: () => void;
}

const DeleteConfirmationModal = (props: DeleteConfirmationModalProps) => {
    const { show, handleClose, handleConfirm } = props;

    const handleCloseKeyDown = (event: React.KeyboardEvent<HTMLDivElement>) => {
        if (event.key === 'Enter' || event.key === ' ') {
            handleClose();
        }
    };

    const handleConfirmKeyDown = (event: React.KeyboardEvent<HTMLDivElement>) => {
        if (event.key === 'Enter' || event.key === ' ') {
            handleConfirm();
        }
    };

    return (
        show && (
            <div className="modal-container">
                <div className="modal-content">
                    <div className="modal-header">
                        <div className="centered-content">
                            <WarningIcon />
                            <div className="modal-title">Bestätigung</div>
                        </div>
                    </div>
                    <div className="modal-body">
                        <p>Sind Sie sicher, dass Sie diesen Versicherungseintrag löschen möchten?</p>
                    </div>
                    <div className="modal-footer">
                        <div
                            className="button-cancel"
                            onClick={handleClose}
                            onKeyDown={handleCloseKeyDown}
                            tabIndex={0}
                        >
                            Abbrechen
                        </div>
                        <div
                            className="button-confirm"
                            onClick={handleConfirm}
                            onKeyDown={handleConfirmKeyDown}
                            tabIndex={0}
                        >
                            Löschen
                        </div>
                    </div>
                </div>
            </div>
        )
    );
};

export default DeleteConfirmationModal;