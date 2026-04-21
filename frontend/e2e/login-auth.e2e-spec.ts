describe('Login and auth flow', () => {
  it('redirects to /login when opening protected route without credentials', () => {
    cy.visit('/empleados');
    cy.url().should('include', '/login');
  });

  it('allows access to /empleados after valid login (placeholder)', () => {
    cy.visit('/login');
    cy.get('body').should('exist');
  });

  it('clears session and returns to /login on logout (placeholder)', () => {
    cy.visit('/');
    cy.get('body').should('exist');
  });
});
