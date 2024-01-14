import "./StatisticsPage.css";
import {useEffect, useState} from "react";
import axios, {AxiosError} from "axios";
import {Bar} from "react-chartjs-2";
import {Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend} from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

interface InsuranceSummaryDTO {
    totalAmount: number;
    lifeInsuranceCount: number;
    propertyInsuranceCount: number;
    vehicleInsuranceCount: number;
}

function StatisticsPage() {

    const [insuranceSummary, setInsuranceSummary] = useState<InsuranceSummaryDTO>();

    useEffect(() => {
        axios.get('/api/summary')
            .then((response) => {
                setInsuranceSummary(response.data);
            })
            .catch((error: AxiosError) => {
                console.error('Error fetching insurance summary:', error);
            });
    }, []);

    const formatCurrency = (amount: number): string => {
        return new Intl.NumberFormat('de-DE', {style: 'currency', currency: 'EUR'}).format(amount);
    };

    const chartData = {
        labels: ["Lebensversicherungen", "Immobilienversicherungen", "Kfz-Versicherungen"],
        datasets: [
            {
                label: "Anzahl der Policen",
                data: insuranceSummary ? [insuranceSummary.lifeInsuranceCount, insuranceSummary.propertyInsuranceCount, insuranceSummary.vehicleInsuranceCount] : [],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.6)',
                    'rgba(54, 162, 235, 0.6)',
                    'rgba(255, 206, 86, 0.6)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)'
                ],
                borderWidth: 1
            }
        ]
    };

    const chartOptions = {
        maintainAspectRatio: false,
        plugins: {
            legend: {
                display: false,
            },
        },
        scales: {
            y: {
                beginAtZero: true,
                ticks: {
                    stepSize: 1,
                    precision: 0
                }
            }
        }
    };

    return (
        <div className="statistics-container">
            <h1 className="statistics-title">Statistiken</h1>
            <h2 className="chart-title">Anzahl der Policen</h2>
            <div className="chart-container">
                <Bar data={chartData} options={chartOptions}/>
            </div>
            <br/>
            <div className="total-amount">
                <span
                    className="bold-text">Gesamtbetrag:</span> {formatCurrency(insuranceSummary ? insuranceSummary.totalAmount : 0)}
            </div>
        </div>
    )
}

export default StatisticsPage