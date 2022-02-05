RSpec.describe 'SMTP4dev Container' do
  it 'Returns 200 when container has started' do
    response = RestClient.get('http://localhost:3000/')
    expect(response).to include 'smtp4dev'
  end
end
