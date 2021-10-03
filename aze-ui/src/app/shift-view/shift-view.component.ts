import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Shift, ShiftSucess} from '../_models/shift';
import {environment} from '../../environments/environment';
import jwt_decode from 'jwt-decode';
import {Token} from '../_models/token';
import {User} from '../_models/user';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';


@Component({
    selector: 'app-shift-view',
    templateUrl: './shift-view.component.html',
    styleUrls: ['./shift-view.component.scss']
})
export class ShiftViewComponent implements OnInit {
    users: User[] = [];
    id: number | undefined;
    displayedColumns: string[] = ['id', 'begin', 'end', 'worktime'];
    isLoading = true;
    isAdmin = false;
    begin = '';
    end = '';
    dataSource: any;
    @ViewChild(MatSort) sort: MatSort;
    @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;

    constructor(private http: HttpClient) {
    }

    ngOnInit(): void {
        this.id = this.getIdFromToken()?.id;
        let begin = this.getTimeString('begin');
        let end = this.getTimeString('end');

        this.http.get<Shift[]>(environment.api + `/shift/get/all/?id=${this.id}&begin=${begin}&end=${end}`)
            .subscribe(data => {
                this.dataSource = new MatTableDataSource<Shift>(data);
            }, error => {
                console.log(error);
            }, () => {
                this.setOvertime();
                this.paginator._intl.itemsPerPageLabel = '';
                this.dataSource.paginator = this.paginator;
                this.dataSource.sortingDataAccessor = (item: Shift, property) => {
                    if (property === 'begin') {
                        return new Date(item.begin);
                    } else if (property === 'end') {
                        return new Date(item.end);
                    } else {
                        return item[property];
                    }
                };
                this.dataSource.sort = this.sort;

                this.isLoading = false;
            });
        this.http.get<User[]>(environment.api + `/users/get/all`)
            .subscribe(data => {
                this.users = data;
            }, () => {
                this.isAdmin = false;
            }, () => {
                this.isAdmin = true;
            });
    }

    private setOvertime() {
        this.dataSource.data.forEach(function(shiftRecent, index, array) {
            if (index > 0) {
                let startTime = new Date(shiftRecent.begin);
                let difference: number;
                let endYesterday = new Date(array[index - 1].end).getTime();
                difference = startTime.getTime() - endYesterday;
                let difference_hours = difference / (1000 * 3600);

                shiftRecent.dailyOvertimed = difference_hours < 11;
            }
        });
    }

    getIdFromToken(): Token | null {
        const token = localStorage.getItem('token');
        if (token) {
            return jwt_decode(token);
        } else {
            return null;
        }
    }


    changeEmployeeList(value: number): void {
        let begin = this.getTimeString('begin');
        let end = this.getTimeString('end');

        this.http.get<Shift[]>(environment.api + `/shift/get/all/?id=${value}&begin=${begin}&end=${end}`)
            .subscribe(data => {
                this.dataSource.data = data;
            }, error => {
                console.log(error);
            }, () => {
                this.setOvertime();
                this.isLoading = false;
            });
    }

    retrieveWithTime() {
        let begin = this.getTimeString('begin');
        let end = this.getTimeString('end');

        this.http.get<Shift[]>(environment.api + `/shift/get/all/?id=${this.id}&begin=${begin}&end=${end}`)
            .subscribe(data => {
                this.dataSource.data = data;
            }, error => {
                console.log(error);
            }, () => {
                this.setOvertime();
                this.isLoading = false;
            });
    }

    startShift() {
        this.http.put<ShiftSucess>(environment.api + `/shift/begin?id=${this.id}`, {})
            .subscribe(data => {
                //intentional
            }, error => {
                console.error(error);
            }, () => {
                this.retrieveWithTime();
            });
    }

    endShift() {
        this.http.put<ShiftSucess>(environment.api + `/shift/end?id=${this.id}`, {})
            .subscribe(data => {
                //intentional
            }, error => {
                console.error(error);
            }, () => {
                this.retrieveWithTime();
            });
    }

    hasActiveShift(): boolean {
        let hasActiveShift = false;
        if (this.dataSource) {
            this.dataSource.data.forEach(x => {
                if (x.end == null) {
                    hasActiveShift = true;
                }
            });
        }
        return hasActiveShift;
    }

    getTimeString(value: string) {
        let date;
        if (value == 'begin') {
            if (this.begin === '') {
                date = '01.01.1970';
            } else {
                date = this.getLocalDate(this.begin);
            }
        }
        if (value == 'end') {
            if (this.end === '' || this.end == null) {
                date = '01.01.2100';
            } else {
                date = this.getLocalDate(this.end);
            }
        }
        return date;
    }

    getLocalDate(value: string) {
        return new Date(value).toLocaleDateString('de-DE', {year: 'numeric', month: '2-digit', day: '2-digit'});
    }

    isValid(shift: Shift, shiftYesterday: Shift) {
        let startTime = new Date(shift.begin);
        let difference: number;
        let endYesterday = new Date(shiftYesterday.end).getTime();
        difference = startTime.getTime() - endYesterday;
        let difference_hours = difference / (1000 * 3600);

        return difference_hours < 11;
    }

    downloadPdf() {
        let prepare = [];
        this.dataSource.data.forEach(item => {
            let tempObj = [];
            tempObj.push(item.begin);
            tempObj.push(item.end);
            tempObj.push(item.worktime);
            prepare.push(tempObj);
        });
        const doc: jsPDF | any = new jsPDF();
        autoTable(doc, {
            head: [['Anfang', 'Ende', 'Arbeitszeit']],
            body: prepare
        });
        let name = this.getIdFromToken()?.name;
        doc.save('Arbeitszeit_' + name + '.pdf');
    }

}
