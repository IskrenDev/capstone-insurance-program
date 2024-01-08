import "./StatisticsPage.css";
import {useEffect, useState} from "react";
import axios, {AxiosError, AxiosResponse} from "axios";

function StatisticsPage() {

    const [totalAmount, setTotalAmount] = useState<number>(0);

    useEffect(() => {
        axios
            .get(`/api/summary/total-amount`)
            .then((response: AxiosResponse) => {
                setTotalAmount(response.data);
            })
            .catch((error: AxiosError) => {
                console.error('Error fetching total amount:', error);
            });
    }, []);

    const formatCurrency = (amount: number): string => {
        return new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'EUR' }).format(amount);
    };

    return (
        <div className="statistics-container">
            <h1 className="statistics-title">Statistiken</h1>
            <div className="total-amount">
                <span className="bold-text">Gesamtbetrag:</span> {formatCurrency(totalAmount)}
            </div>
        </div>
    )
}

export default StatisticsPage