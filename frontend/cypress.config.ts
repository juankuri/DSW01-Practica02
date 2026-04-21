import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:4200',
    specPattern: 'e2e/**/*.e2e-spec.ts',
    supportFile: false,
  },
});
