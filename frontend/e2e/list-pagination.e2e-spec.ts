describe('List pagination', () => {
  it('renders empleados metadata (placeholder)', () => {
    cy.visit('/empleados');
    cy.get('body').should('exist');
  });

  it('renders departamentos metadata (placeholder)', () => {
    cy.visit('/departamentos');
    cy.get('body').should('exist');
  });
});
