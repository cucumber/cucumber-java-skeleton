require 'sinatra'
require 'json'

set :bind, '0.0.0.0'
set :port, 9944

$users = []

class User
  attr_reader :email
  def initialize(email, fn, ln)
    @email = email
    @first_name = fn
    @last_name = ln
  end

  def as_hash
    hash = {}
    self.instance_variables.each do |var|
      sym = var[1..-1].to_sym
      hash[sym] = self.instance_variable_get var
    end
    hash
  end

  def to_json(*args)
    as_hash.to_json
  end

end

get '/math/plus' do
  headers \
    'content-type' => 'application/json;charset=utf-8'
  begin
    a = params[:a].to_i
    b = params[:b].to_i
    json_response(result: a+b)
  rescue => exc
    status 422
    return json_error("Something went wrong: #{exc}")
  end
end

get '/math/divide' do
  headers \
    'content-type' => 'application/json;charset=utf-8'
  begin
    a = params[:a].to_f
    b = params[:b].to_f
    json_response(result: a/b)
  rescue => exc
    status 422
    return json_error("Something went wrong: #{exc}")
  end
end

get '/users' do
  headers \
    'content-type' => 'application/json;charset=utf-8'
  email = params[:email]
  if email.nil?
    status 200
    return json_response(users: $users)
  else
    if !user_exists?(email)
      status 404
      return json_error("User with '#{email}' not found")
    else
      status 200
      return json_response(user: get_user(email))
    end
  end
end

delete '/users' do
  $users = []
end

post '/users/add' do
  headers \
    'content-type' => 'application/json;charset=utf-8'
  data = JSON.parse request.body.read
  first_name = data['firstName']
  last_name = data['lastName']
  email = data['email']
  required = [first_name, last_name, email]
  if required.include?(nil)
    # bad error example
    return json_error("Required parameter should not be nil")
  else
    if user_exists?(email)
      return json_error("User with email: #{email} already exists")
    else
      status 201
      user = add_user(email, last_name, first_name)
      return json_response(user: user)
    end
  end
end

get '*' do
  headers \
    'content-type' => 'application/json;charset=utf-8'
  status 422
  return json_error("unknown route: #{request.url}")
end

post '*' do
  headers \
    'content-type' => 'application/json;charset=utf-8'
  status 422
  return json_error("unknown route: #{request.url}")
end


private

def add_user(email, last_name, first_name)
  user = User.new email, last_name, first_name
  $users << user
  return user
end

def get_user(email)
  $users.select{|u| u.email == email}[0]
end

def user_exists?(email)
   !get_user(email).nil?
end

def json_response(hsh)
  JSON.dump hsh
end

def json_error(msg, usage=nil)
  h = {}
  h[:error] = msg
  h[:usage] = usage unless usage.nil?
  return h.to_json
end

