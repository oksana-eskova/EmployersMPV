package com.oieskova.employers2.data;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.GridLayout;

import com.oieskova.employers2.api.ApiFactory;
import com.oieskova.employers2.api.ApiService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeViewModel extends AndroidViewModel {
    private static AppDatabase database;
    private LiveData<List<Employee>> employees;
    private MutableLiveData<Throwable> errors;


    private CompositeDisposable compositeDisposable;

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        database=AppDatabase.getInstance(getApplication());
        employees=database.employeeDao().getAllEmployees();
        errors=new MutableLiveData<>();
        //specialities=new MutableLiveData<>();
    }

    private void insertEmployees(List<Employee> employees){
        new InsertTask().execute(employees);
    }
    private static class InsertTask extends AsyncTask<List<Employee>,Void,Void>{

        @Override
        protected Void doInBackground(List<Employee>... lists) {
            if(lists!=null&&lists.length>0){
                database.employeeDao().insertEmployees(lists[0]);
            }
            return null;
        }
    }

    private void deleteAllEmployees(){
        new DeleteTask().execute();
    }
    private static class DeleteTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            database.employeeDao().deleteAllEmployees();
            return null;
        }
    }
    public LiveData<Employee> getEmployeeById(int employeeId){
        LiveData<Employee> result=null;
        try {
            result=new GetEmployeeTask().execute(employeeId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    private static class GetEmployeeTask extends AsyncTask<Integer,Void,LiveData<Employee>>{

        @Override
        protected LiveData<Employee> doInBackground(Integer... integers) {
            LiveData<Employee> result=null;
            if(integers!=null&&integers.length>0){
               result= database.employeeDao().getEmployeeById(integers[0]);
            }
            return result;
        }
    }
    public void loadData(){
        compositeDisposable=new CompositeDisposable();
        //Загрузка данных
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService=apiFactory.getApiService();
        Disposable disposable=apiService.getEmployers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Employers>() {
                    @Override
                    public void accept(Employers result) throws Exception {
                        deleteAllEmployees();
                        insertEmployees(result.getResponse());
                       /* List<Speciality> specialitiesFromEmloyees=getSpecialities(result.getResponse());
                        specialities.setValue(specialitiesFromEmloyees);*/

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        if(compositeDisposable!=null) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }
    public static List<Speciality> getSpecialities(List<Employee> employees){
        HashSet<Speciality> set=new HashSet<>();
        for(Employee employee:employees){
            List<Speciality> specialitiesOfPerson=employee.getSpecialities();
            for(Speciality speciality:specialitiesOfPerson){
                set.add(speciality);
            }
        }
        ArrayList<Speciality> specialities=new ArrayList<>();
        specialities.addAll(set);
        return specialities;
    }
    public static List<Employee> getEmployeesForSpeciality(List<Employee> employees, int specialityId){
        List<Employee> result=new ArrayList<>();
        for(Employee employee:employees){
            for(Speciality speciality:employee.getSpecialities()){
                if(speciality.getSpecialtyId()==specialityId){
                    result.add(employee);
                    break;
                }
            }
        }
        return result;
    }

     public void clearErrors(){
        errors.setValue(null);
    }

}
