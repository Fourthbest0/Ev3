import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private oauth: OAuthService) {}

  /** Inicia el flujo Authorization Code + PKCE contra el auth-server. */
  login(): void {
    this.oauth.initCodeFlow();
  }

  /** Cierra sesión local y en el Authorization Server (OIDC RP-Initiated Logout). */
  logout(): void {
    const idToken = this.oauth.getIdToken();
    this.oauth.logOut(true);
    const params = new URLSearchParams({
      id_token_hint: idToken,
      post_logout_redirect_uri: environment.postLogoutRedirectUri,
      client_id: environment.clientId
    });
    window.location.href = `${environment.authServerUrl}/connect/logout?${params.toString()}`;
  }

  token(): string {
    return this.oauth.getAccessToken();
  }

  isLoggedIn(): boolean {
    return this.oauth.hasValidAccessToken();
  }

  username(): string {
    try {
      const claims = this.oauth.getIdentityClaims();
      return claims?.['preferred_username'] ?? claims?.['sub'] ?? '';
    } catch {
      return '';
    }
  }
}
