import {environment} from './environments/environment';
import {enableProdMode} from '@angular/core';
import {bootstrapApplication} from '@angular/platform-browser';
import {AppComponent} from './app/app.component';
import {appConfig} from './app/app.config';
import {registerLocaleData} from '@angular/common';
import * as fr from '@angular/common/locales/fr';


if (environment.production) {
  enableProdMode();
}

registerLocaleData(fr.default);
bootstrapApplication(AppComponent, appConfig)
  .catch(err => console.error(err));
