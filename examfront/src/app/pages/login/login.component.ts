import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private snack:MatSnackBar,private login:LoginService,private router:Router) {}

  loginData = {
    username:'',
    password:'',
  };
  formSubmit() {
    console.log("login btn click");
    if (this.loginData.username.trim() == '' || this.loginData.username == null) {
      this.snack.open("Username is required !!",'',{
        duration:3000,
      });
      return;
    }

    if (this.loginData.password.trim() == '' || this.loginData.password == null) {
      this.snack.open("Password is required !!",'',{
        duration:3000,
      });
      return;
    }

    // request to server to generate token

    this.login.generateToken(this.loginData).subscribe(
      (data:any) => {
        console.log("success");
        console.log(data);

        // login ...
        
        this.login.loginUser(data.token);
        

        this.login.getCurrentUser().subscribe(
          (user:any) => {
            

            this.login.setUser(user);
            
            console.log(user);

            // redirect ... ADMIN: admin-dashboard
            // redirect ... NORMAL: normal-dashboard

            if(this.login.getUserRole() == "ADMIN") {
                // ADMIN DASHBOARD
                // window.location.href='/admin'
                this.router.navigate(['admin']);
                this.login.loginStatusSubject.next(true);
            }
            else if (this.login.getUserRole() == "NORMAL") {
                // NORMAL DASHBOARD
                // window.location.href='/user-dashboard'
                this.router.navigate(['user-dashboard/0']);
                this.login.loginStatusSubject.next(true);
            }
            else {
              this.login.logout();
            }

            

          }
        );
      },
      (error) => {
        console.log("error");
        console.log(error);  
        this.snack.open("Invalid Details !! Try Again",'',{
          duration:3000,
        })
      }
    )

  }

}
