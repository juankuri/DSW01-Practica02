describe('Departamentos CRUD', () => {
  it('creates, edits and deletes departamento (placeholder)', () => {
    cy.visit('/departamentos');
    cy.get('body').should('exist');
  });

  it('shows integrity-conflict feedback on delete (placeholder)', () => {
    cy.visit('/departamentos');
    cy.get('body').should('exist');
  });
});
